package com.sherwin.conference.bookingsystem.infrastructure.dto.ticket;

import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;

import java.time.LocalDateTime;

public record TicketDto(Long id, Long talkId, String userEmail, String status, LocalDateTime reservedAt) {
  public static TicketDto fromDomain(Ticket ticket) {
    return new TicketDto(
        ticket.ticketId().value(),
        ticket.talkId().value(),
        ticket.userEmail().value(),
        ticket.reservationStatus().name(),
        ticket.reservedAt().value());
  }
}
