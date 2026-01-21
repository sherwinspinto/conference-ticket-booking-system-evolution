package com.sherwin.conference.bookingsystem.domain.event.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.api.port.DomainEventHandlerPort;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TicketId;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port.ReservedSeatEventHandlerPort;
import java.time.LocalDateTime;

@DomainService
public class DomainEventHandlerService
    implements DomainEventHandlerPort<ConferenceApplicationEvent> {
  private final ReservedSeatEventHandlerPort reservedTicketEventHandler;

  public DomainEventHandlerService(ReservedSeatEventHandlerPort reservedTicketEventHandler) {
    this.reservedTicketEventHandler = reservedTicketEventHandler;
  }

  @Override
  public void handle(ConferenceApplicationEvent event) {
    switch (event) {
      case ConferenceApplicationEvent.ReservedTicket reservedTicket ->
          reservedTicketEventHandler.handleReservedSeatEvent(reservedTicket);
      case ConferenceApplicationEvent.ReservationExpired expiredTicket -> {
        IO.println(event);
      }
    }
  }
}
