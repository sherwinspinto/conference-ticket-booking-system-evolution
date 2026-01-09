package com.sherwin.conference.bookingsystem.domain.feature;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

import com.sherwin.conference.bookingsystem.domain.ReservationResult;
import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent.SeatReserved;
import com.sherwin.conference.bookingsystem.domain.spi.ExpireOldReservationsAction;
import com.sherwin.conference.bookingsystem.entity.TicketEntity;
import com.sherwin.conference.bookingsystem.infrastructure.repository.TicketRepository;
import com.sherwin.conference.bookingsystem.infrastructure.service.TalkService;
import com.sherwin.conference.bookingsystem.infrastructure.service.TicketService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@SpringBootTest
public class SeatExpiryDomainServiceTest {

  @MockitoSpyBean private ExpireOldReservationsAction expireOldReservationsAction;

  @MockitoBean(name = "taskScheduler")
  private ScheduledExecutorService executorService;

  @MockitoBean(name = "virtualExecutor")
  private ExecutorService virtualThreadExecutorService;

  @Autowired private SeatExpiryDomainService seatExpiryDomainService;

  @Autowired TalkService talkDomainService;

  @Autowired TicketService ticketDomainService;

  @MockitoSpyBean DomainEventHandler eventHandler;

  @Autowired TicketRepository ticketRepository;

  @Test
  void testScheduleSeatExpiry() throws Exception {
    long ticketId = 0L;
    LocalDateTime reservedAt = LocalDateTime.now();
    SeatReserved seatReserved = new SeatReserved(ticketId, reservedAt);

    ArgumentCaptor<Runnable> virtualThreadJobCaptor = ArgumentCaptor.forClass(Runnable.class);
    ArgumentCaptor<Callable<Long>> virtualThreadCallable = ArgumentCaptor.forClass(Callable.class);

    seatExpiryDomainService.expireSeat(seatReserved);

    verify(executorService).schedule(virtualThreadJobCaptor.capture(), anyLong(), any());

    virtualThreadJobCaptor.getValue().run();

    verify(virtualThreadExecutorService).submit(virtualThreadCallable.capture());

    virtualThreadCallable.getValue().call();

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
        seatExpiryDomainService.calculateDelayForSeatExpiryScheduledJobInMillis(
            reservedAt, expireAfterDuration, schedulerNowTime));
  }

  @Test
  void testSeatExpiryFlow() throws Exception {
    String name = "talk1";
    int totalSeats = 10;
    ArgumentCaptor<ConferenceApplicationEvent> seatReservedArgumentCaptor =
        ArgumentCaptor.forClass(ConferenceApplicationEvent.class);
    ArgumentCaptor<Runnable> virtualThreadJobCaptor = ArgumentCaptor.forClass(Runnable.class);
    ArgumentCaptor<Callable<Long>> virtualThreadCallable = ArgumentCaptor.forClass(Callable.class);
    Talk talk = talkDomainService.create(name, totalSeats);
    String userEmail = "testuser@testuser.com";

    String apiReturn = ticketDomainService.reserveTicket(talk.id(), userEmail);
    verify(eventHandler).handleEvent(seatReservedArgumentCaptor.capture());
    verify(executorService).schedule(virtualThreadJobCaptor.capture(), anyLong(), any());
    virtualThreadJobCaptor.getValue().run();
    verify(virtualThreadExecutorService).submit(virtualThreadCallable.capture());
    ConferenceApplicationEvent conferenceApplicationEvent = seatReservedArgumentCaptor.getValue();
    switch (conferenceApplicationEvent) {
      case SeatReserved seatReserved -> {
        virtualThreadCallable.getValue().call();
        TicketEntity ticket = ticketRepository.findById(seatReserved.ticketId()).orElseThrow();
        Assertions.assertEquals(
            new ReservationResult.Expired().toString(), ticket.getStatus().toString());
      }
      case ConferenceApplicationEvent.SeatExpired _ -> IO.println("Nothing");
    }
  }
}
