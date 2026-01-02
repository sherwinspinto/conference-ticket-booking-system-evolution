package com.sherwin.conference.bookingsystem.domain.feature;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent.SeatReserved;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent.SeatExpired;
import com.sherwin.conference.bookingsystem.domain.spi.ExpireOldReservationsAction;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootTest
class AfterSeatExpiryDomainServiceTest {
  @MockitoBean private AfterSeatExpiryService afterSeatExpiryService;

  @Autowired private SeatExpiryDomainService seatExpiryDomainService;

  @MockitoBean private ExpireOldReservationsAction expireOldReservationsAction;
  @MockitoBean(name = "taskScheduler") private ScheduledExecutorService taskScheduler;
  @MockitoBean(name = "virtualExecutor") private ExecutorService virtualExecutor;

  @Test
  public void testAfterSeatExpiryEventReceived() throws Exception {
    Long ticketId = 0L;
    LocalDateTime reservedAt = LocalDateTime.now();
    SeatReserved seatReserved = new SeatReserved(ticketId, reservedAt);
    ArgumentCaptor<SeatExpired> seatExpiredArgumentCaptor = ArgumentCaptor.forClass(SeatExpired.class);
    ArgumentCaptor<Runnable> schedulerThreadJob = ArgumentCaptor.forClass(Runnable.class);
    ArgumentCaptor<Callable<Long>> virtualThreadJob = ArgumentCaptor.forClass(Callable.class);

    seatExpiryDomainService.expireSeat(seatReserved);

    verify(taskScheduler).schedule(schedulerThreadJob.capture(), anyLong(), any());

    schedulerThreadJob.getValue().run();

    verify(virtualExecutor).submit(virtualThreadJob.capture());

    virtualThreadJob.getValue().call();

    verify(afterSeatExpiryService).handleSeatExpiryEvent(seatExpiredArgumentCaptor.capture());

    assertInstanceOf(SeatExpired.class, seatExpiredArgumentCaptor.getValue());
  }
}
