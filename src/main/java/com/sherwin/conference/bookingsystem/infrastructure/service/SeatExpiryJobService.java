package com.sherwin.conference.bookingsystem.infrastructure.service;

import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.spi.EventPublisher;
import com.sherwin.conference.bookingsystem.domain.spi.ExpireOldReservationsAction;
import com.sherwin.conference.bookingsystem.domain.spi.SeatExpiryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SeatExpiryJobService implements SeatExpiryService {
  private final ExpireOldReservationsAction expireOldReservationsAction;
  private final EventPublisher eventPublisher;

  public SeatExpiryJobService(ExpireOldReservationsAction expireOldReservationsAction, EventPublisher eventPublisher) {
    this.expireOldReservationsAction = expireOldReservationsAction;
    this.eventPublisher = eventPublisher;
  }

  @Override
  @Transactional
  public boolean expireSeat(Long ticketId) {
    expireOldReservationsAction.updateTicketStatusToExpired(ticketId);
    eventPublisher.publish(
      new ConferenceApplicationEvent.ReservationExpired(ticketId, LocalDateTime.now()), Boolean.TRUE);
    return Boolean.TRUE;
  }
}
