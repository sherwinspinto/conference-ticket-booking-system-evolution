package com.sherwin.conference.bookingsystem.domain.feature.ticket.model;

import static com.sherwin.conference.bookingsystem.domain.feature.ticket.model.TicketFieldType.*;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;

public record ReserveTicket(
  TalkId talkId, Email userEmail, ReservationStatus reservationStatus, ReservedAt reservedAt)
    implements TicketModel {}
