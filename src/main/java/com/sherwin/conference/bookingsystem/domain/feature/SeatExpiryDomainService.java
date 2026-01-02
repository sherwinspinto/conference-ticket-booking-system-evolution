package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent.SeatReserved;
import com.sherwin.conference.bookingsystem.domain.spi.EventPublisher;
import com.sherwin.conference.bookingsystem.domain.spi.ExpireOldReservationsAction;
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

  public boolean expireSeat(SeatReserved seatReserved) {
    long expireAfter = 1;
    TimeUnit expireAfterTimeUnit = TimeUnit.MINUTES;
    Duration expireAfterDuration = Duration.of(expireAfter, expireAfterTimeUnit.toChronoUnit());

    long delay =
        calculateDelayForSeatExpiryScheduledJobInMillis(
            seatReserved.reservedAt(), expireAfterDuration, LocalDateTime.now());
    return expireSeat(seatReserved, delay, TimeUnit.MILLISECONDS);
  }

  public boolean expireSeat(SeatReserved seatReserved, long delay, TimeUnit timeUnit) {
    taskScheduler.schedule(
        () -> updateStatusToExpiredInThread(seatReserved.ticketId()),
        delay,
        timeUnit);
    LOGGER.info(
        "Scheduled Expiry Job for ticket: {} ----- with Delay: {} ----- TimeUnit: {} ",
        seatReserved.ticketId(),
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
