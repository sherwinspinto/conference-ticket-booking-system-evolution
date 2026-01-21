package com.sherwin.conference.bookingsystem.domain.feature.ticket.model;

import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TicketId;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.TicketFieldType.Email;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.TicketFieldType.ReservedAt;
import java.time.LocalDateTime;

public record Ticket(
    TicketId ticketId,
    TalkId talkId,
    Email userEmail,
    ReservedAt reservedAt,
    ReservationStatus reservationStatus)
    implements TicketModel {
  public static CreationResult<Ticket> of(
      Long ticketId,
      Long talkId,
      String userEmail,
      LocalDateTime reservedAt,
      ReservationStatus reservationStatus) {
    return CreationResult.success(
        new Ticket(
            new TicketId(ticketId),
            new TalkId(talkId),
            new Email(userEmail),
            new ReservedAt(reservedAt),
          reservationStatus));
  }
}
