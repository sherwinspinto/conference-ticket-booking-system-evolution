package com.sherwin.conference.bookingsystem.domain.feature;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent.SeatReserved;
import com.sherwin.conference.bookingsystem.domain.spi.ExpireOldReservationsAction;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class SeatExpiryServiceTest {

  @MockitoBean private ExpireOldReservationsAction expireOldReservationsAction;

  @MockitoBean(name = "taskScheduler")
  private ScheduledExecutorService executorService;

  @MockitoBean(name = "virtualExecutor")
  private ExecutorService virtualThreadExecutorService;

  @Autowired private SeatExpiryService seatExpiryService;

  @Test
  void testScheduleSeatExpiry() throws Exception {
    long ticketId = 0L;
    LocalDateTime reservedAt = LocalDateTime.now();
    SeatReserved seatReserved = new SeatReserved(ticketId, reservedAt);

    ArgumentCaptor<Runnable> virtualThreadJobCaptor = ArgumentCaptor.forClass(Runnable.class);
    ArgumentCaptor<Runnable> virtualThreadCallable = ArgumentCaptor.forClass(Runnable.class);

    seatExpiryService.expireSeat(seatReserved);

    verify(executorService).schedule(virtualThreadJobCaptor.capture(), anyLong(), any());

    virtualThreadJobCaptor.getValue().run();

    verify(virtualThreadExecutorService).submit(virtualThreadCallable.capture());

    virtualThreadCallable.getValue().run();

    verify(expireOldReservationsAction).updateTicketStatusToExpired(any());
  }

  @Test
  public void testCalculateDelayForSeatExpiryScheduledJobInMillis() {
    TimeUnit expireAfterTimeUnit = TimeUnit.MINUTES;
    long expireAfter = 10;
    Duration expireAfterDuration = Duration.of(expireAfter, expireAfterTimeUnit.toChronoUnit());

    LocalDateTime reservedAt = LocalDateTime.now();

    LocalDateTime schedulerNowTime = LocalDateTime.now().plusSeconds(2);

    Assertions.assertEquals(
        598_000,
        seatExpiryService.calculateDelayForSeatExpiryScheduledJobInMillis(
            reservedAt, expireAfterDuration, schedulerNowTime));
  }
}
