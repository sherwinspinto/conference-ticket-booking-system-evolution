package com.sherwin.conference.bookingsystem.domain.feature.ticket.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.event.model.HandlerResult;
import com.sherwin.conference.bookingsystem.domain.event.spi.DomainEventPublisherPort;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TicketId;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port.ReservedSeatEventHandlerPort;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port.SetTicketStatusToExpiredPort;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.SetTicketStatusToExpired;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.TicketFieldType;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;

@DomainService
public class ReservedTicketEventHandler implements ReservedSeatEventHandlerPort {
  private static final Logger LOGGER = Logger.getLogger(ReservedTicketEventHandler.class.getName());
  private final ScheduledExecutorService scheduledExecutorService;
  private final ExecutorService virtualThreadExecutor;
  private final SetTicketStatusToExpiredPort setTicketStatusToExpiredPort;
  private final DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisherPort;
  private final Clock clock;
  private static final int ONE_MINUTE_IN_SECONDS = 60;
  private static final Duration EXPIRY_DURATION_SECONDS = Duration.ofSeconds(2 * ONE_MINUTE_IN_SECONDS);

  public ReservedTicketEventHandler(
      ScheduledExecutorService scheduledExecutorService,
      ExecutorService virtualThreadExecutor,
      SetTicketStatusToExpiredPort setTicketStatusToExpiredPort,
      DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisherPort,
      Clock clock) {
    this.scheduledExecutorService = scheduledExecutorService;
    this.virtualThreadExecutor = virtualThreadExecutor;
    this.setTicketStatusToExpiredPort = setTicketStatusToExpiredPort;
    this.eventPublisherPort = eventPublisherPort;
    this.clock = clock;
  }

  @Override
  public HandlerResult handleReservedSeatEvent(ConferenceApplicationEvent.ReservedTicket event) {
    scheduledExecutorService.schedule(
        new ScheduledTask(
            new TicketId(event.ticketId()),
            virtualThreadExecutor,
            setTicketStatusToExpiredPort,
            eventPublisherPort,
            clock),
        calculateEventJitterInSeconds(
            new TicketFieldType.ReservedAt(event.reservedAt()), EXPIRY_DURATION_SECONDS, clock),
        TimeUnit.SECONDS);
    return HandlerResult.success("Seat reserved successfully for Ticket ID: " + event.ticketId());
  }

  static class ScheduledTask implements Runnable {
    private final TicketId ticketId;
    private final ExecutorService executorService;
    private final SetTicketStatusToExpiredPort setTicketStatusToExpiredPort;
    private final DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisherPort;
    private final Clock clock;

    public ScheduledTask(
        TicketId ticketId,
        ExecutorService executorService,
        SetTicketStatusToExpiredPort setTicketStatusToExpiredPort,
        DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisherPort,
        Clock clock) {
      this.ticketId = ticketId;
      this.executorService = executorService;
      this.setTicketStatusToExpiredPort = setTicketStatusToExpiredPort;
      this.eventPublisherPort = eventPublisherPort;
      this.clock = clock;
    }

    @Override
    public void run() {
      executorService.submit(
          new ExpireTicketTask(ticketId, setTicketStatusToExpiredPort, eventPublisherPort, clock));
    }
  }

  public static class ExpireTicketTask implements Runnable {
    private final TicketId ticketId;
    private final SetTicketStatusToExpiredPort setTicketStatusToExpiredPort;
    private final DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisherPort;
    private final Clock clock;

    public ExpireTicketTask(
        TicketId ticketId,
        SetTicketStatusToExpiredPort setTicketStatusToExpiredPort,
        DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisherPort,
        Clock clock) {
      this.ticketId = ticketId;
      this.setTicketStatusToExpiredPort = setTicketStatusToExpiredPort;
      this.eventPublisherPort = eventPublisherPort;
      this.clock = clock;
    }

    @Override
    public void run() {
      Optional<Ticket> updatedTicket =
          setTicketStatusToExpiredPort.setTicketStatusToExpired(
              new SetTicketStatusToExpired(ticketId));

      updatedTicket
          .map(toReservationExpiredEvent(clock))
          .ifPresentOrElse(
              event -> {
                eventPublisherPort.publish(event);
                LOGGER.info(
                    "Successfully published reservation expired event for ticket: " + ticketId);
              },
              () -> LOGGER.warning("Ticket not found, cannot expire: " + ticketId));
    }

    Function<Ticket, ConferenceApplicationEvent.ReservationExpired> toReservationExpiredEvent(
        Clock clock) {
      return ticket ->
          new ConferenceApplicationEvent.ReservationExpired(
              ticket.ticketId().value(), LocalDateTime.now(clock));
    }
  }

  long calculateEventJitterInSeconds(
      TicketFieldType.ReservedAt reservedAt, Duration delay, Clock clock) {
    // Calculate duration since reservation in minutes
    LocalDateTime now = LocalDateTime.now(clock);
    Duration jitterDuration = Duration.between(reservedAt.value(), now);
    if (jitterDuration.isNegative() || jitterDuration.isZero()) return Duration.ZERO.toMinutes();
    return delay.minus(jitterDuration).toSeconds();
  }
}
