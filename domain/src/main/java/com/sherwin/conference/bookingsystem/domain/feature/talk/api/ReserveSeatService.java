package com.sherwin.conference.bookingsystem.domain.feature.talk.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.ReserveSeatPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.ReserveSeatDbPort;

import java.util.Optional;

@DomainService
public class ReserveSeatService implements ReserveSeatPort {
  private final ReserveSeatDbPort reserveSeatDbPort;

  ReserveSeatService(ReserveSeatDbPort reserveSeatDbPort) {
    this.reserveSeatDbPort = reserveSeatDbPort;
  }

  @Override
  public Optional<Talk> reserveSeat(Talk talk) {
    return talk.tryReserveSeat().map(reserveSeatDbPort::reserveSeat);
  }
}
