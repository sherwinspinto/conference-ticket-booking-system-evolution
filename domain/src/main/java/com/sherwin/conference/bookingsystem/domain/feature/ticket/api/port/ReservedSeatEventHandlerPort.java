package com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port;

import static com.sherwin.conference.bookingsystem.domain.feature.model.Id.*;

import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.event.model.HandlerResult;

public interface ReservedSeatEventHandlerPort {
  HandlerResult handleReservedSeatEvent(ConferenceApplicationEvent.ReservedTicket event);
}
