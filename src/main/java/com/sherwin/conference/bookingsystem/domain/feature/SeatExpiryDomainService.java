package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent.ReservedTicket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sherwin.conference.bookingsystem.domain.spi.SeatExpiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DomainService
public class SeatExpiryDomainService {
  private static final Logger LOGGER = LoggerFactory.getLogger(SeatExpiryDomainService.class);

  private final ScheduledExecutorService taskScheduler;
  private final ExecutorService virtualExecutor;
  private final SeatExpiryService seatExpiryService;

  public SeatExpiryDomainService(
    ScheduledExecutorService taskScheduler,
    ExecutorService virtualExecutor, SeatExpiryService seatExpiryService) {
    this.taskScheduler = taskScheduler;
    this.virtualExecutor = virtualExecutor;
    this.seatExpiryService = seatExpiryService;
  }

  public boolean expireSeat(ReservedTicket reservedTicket) {
    long expireAfter = 1;
    TimeUnit expireAfterTimeUnit = TimeUnit.MINUTES;
    Duration expireAfterDuration = Duration.of(expireAfter, expireAfterTimeUnit.toChronoUnit());

    long delay =
        calculateDelayForSeatExpiryScheduledJobInMillis(
            reservedTicket.reservedAt(), expireAfterDuration, LocalDateTime.now());
    return expireSeat(reservedTicket, delay, TimeUnit.MILLISECONDS);
  }

  public boolean expireSeat(ReservedTicket reservedTicket, long delay, TimeUnit timeUnit) {
    taskScheduler.schedule(
        () -> updateStatusToExpiredInThread(reservedTicket.ticketId()),
        delay,
        timeUnit);
    LOGGER.info(
        "Scheduled Expiry Job for ticket: {} ----- with Delay: {} ----- TimeUnit: {} ",
        reservedTicket.ticketId(),
        delay,
        timeUnit.name());
    return Boolean.TRUE;
  }

  void updateStatusToExpiredInThread(Long ticketId) {
    virtualExecutor.submit(
        () -> seatExpiryService.expireSeat(ticketId));
  }

  long calculateDelayForSeatExpiryScheduledJobInMillis(
      LocalDateTime reservedAt, Duration expireAfDuration, LocalDateTime currentTime) {
    Duration jitterDuration = calculateJitter(reservedAt, currentTime);
    long delay = expireAfDuration.toMillis() - jitterDuration.toMillis();
    if (delay < 0) return 0;
    return delay;
  }

  Duration calculateJitter(LocalDateTime startTime, LocalDateTime endTime) {
    return Duration.between(startTime, endTime);
  }
}
