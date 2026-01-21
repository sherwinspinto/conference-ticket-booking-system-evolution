package com.sherwin.conference.bookingsystem.domain.mapper;

import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.Ticket;
import com.sherwin.conference.bookingsystem.entity.ReservationResult;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.ticket.TicketEntity;

public class Mapper {
  public static TicketEntity ticketDomainToEntityMapper(Ticket ticket) {
    return new TicketEntity(
        ticket.talkId(),
        ticket.userEmail(),
        reservationResultDomainToEntityMapper(ticket.status()),
        ticket.reservedAt());
  }

  public static Ticket ticketEntityToTicketDomainMapper(TicketEntity ticketEntity) {
    return new Ticket(
        ticketEntity.getId(),
        ticketEntity.getTalkId(),
        ticketEntity.getUserEmail(),
        reservationResultEntityToDomainMapper(ticketEntity.getStatus()),
        ticketEntity.getReservedAt());
  }

  public static ReservationResult reservationResultDomainToEntityMapper(
      com.sherwin.conference.bookingsystem.domain.ReservationResult status) {
    return switch (status) {
      case com.sherwin.conference.bookingsystem.domain.ReservationResult.Reserved reserved ->
          new ReservationResult.Reserved();
      case com.sherwin.conference.bookingsystem.domain.ReservationResult.Paid paid ->
          new ReservationResult.Paid();
      case com.sherwin.conference.bookingsystem.domain.ReservationResult.Expired expired ->
          new ReservationResult.Expired();
    };
  }

  public static com.sherwin.conference.bookingsystem.domain.ReservationResult
      reservationResultEntityToDomainMapper(ReservationResult status) {
    return switch (status) {
      case ReservationResult.Reserved reserved ->
          new com.sherwin.conference.bookingsystem.domain.ReservationResult.Reserved();
      case ReservationResult.Paid paid ->
          new com.sherwin.conference.bookingsystem.domain.ReservationResult.Paid();
      case ReservationResult.Expired expired ->
          new com.sherwin.conference.bookingsystem.domain.ReservationResult.Expired();
    };
  }

  public static Talk talkEntityToDomainMapper(TalkEntity talkEntity) {
    return new Talk(
      talkEntity.getId(),
      talkEntity.getName(),
      talkEntity.getTotalSeats(),
      talkEntity.getReservedSeats(),
      talkEntity.getVersion());
  }

  public static TalkEntity talkDomainToEntityMapper(Talk talk) {
    return new TalkEntity(
        talk.id(), talk.name(), talk.totalSeats(), talk.reservedSeats(), talk.version());
  }
}
