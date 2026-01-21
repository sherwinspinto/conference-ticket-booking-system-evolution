package com.sherwin.conference.bookingsystem.infrastructure.db.entity.ticket;

import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.ReservationStatus;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.ReserveTicket;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "ticket2")
@Table(name = "tickets")
public class TicketEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long talkId;
  private String userEmail;
  @Enumerated(EnumType.STRING)
  private ReservationStatus status; // "RESERVED", "PAID", "EXPIRED"
  private LocalDateTime reservedAt;

  protected TicketEntity() {}

  public TicketEntity(
    Long id, Long talkId, String userEmail, ReservationStatus status, LocalDateTime reservedAt) {
    this.id = id;
    this.talkId = talkId;
    this.userEmail = userEmail;
    this.status = status;
    this.reservedAt = reservedAt;
  }

  public Long getId() {
    return id;
  }

  public Long getTalkId() {
    return talkId;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public ReservationStatus getStatus() {
    return status;
  }

  public LocalDateTime getReservedAt() {
    return reservedAt;
  }

  public static TicketEntity fromDomain(ReserveTicket reserveTicket) {
    return new TicketEntity(
        null,
        reserveTicket.talkId().value(),
        reserveTicket.userEmail().value(),
        reserveTicket.reservationStatus(),
        reserveTicket.reservedAt().value());
  }

  public static Ticket toDomain(TicketEntity ticketEntity) {
    CreationResult<Ticket> creationResult =
        Ticket.of(
            ticketEntity.getId(),
            ticketEntity.getTalkId(),
            ticketEntity.getUserEmail(),
            ticketEntity.getReservedAt(),
            ticketEntity.getStatus());
    return switch (creationResult) {
      case CreationResult.Success(Ticket ticket) -> ticket;
      case CreationResult.Failure<Ticket> _ ->
          throw new RuntimeException("Error converting from TicketEntity to Ticket (Domain)");
    };
  }
}
