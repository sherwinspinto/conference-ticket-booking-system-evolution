package com.sherwin.conference.bookingsystem.domain.feature;

import static org.junit.jupiter.api.Assertions.*;

import com.sherwin.conference.bookingsystem.domain.ReservationResult;
import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.Ticket;
import com.sherwin.conference.bookingsystem.domain.spi.ReserveTicketAction;
import com.sherwin.conference.bookingsystem.infrastructure.service.TicketService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class TicketDomainServiceTest {
  @MockitoBean private DomainEventHandler domainEventHandler;

  @MockitoBean private ReserveTicketAction reserveTicketAction;

  @Autowired private TicketDomainService ticketDomainService;

  @Autowired private TicketService ticketService;

  @Test
  void testEventListener() throws InterruptedException {
    Talk talk = new Talk(0L, "talk1", 100, 0, 0L);
    Long talkId = 0L;
    String email = "testuser@user.com";
    Mockito.when(reserveTicketAction.fetchTalkById(talkId)).thenReturn(Optional.of(talk));
    Mockito.when(reserveTicketAction.saveTalk(talk)).thenReturn(talk);
    Mockito.when(reserveTicketAction.saveTicket(Mockito.any()))
        .thenReturn(
            new Ticket(
                0L,
                0L,
                email,
                new ReservationResult.Reserved(),
                LocalDateTime.now(),
                LocalDateTime.now()));
    ticketService.reserveTicket(talkId, email);
    Mockito.verify(domainEventHandler).handleEvent(Mockito.any());
  }
}
