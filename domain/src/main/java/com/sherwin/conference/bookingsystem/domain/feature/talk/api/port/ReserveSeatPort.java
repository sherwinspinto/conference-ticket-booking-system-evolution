package com.sherwin.conference.bookingsystem.domain.feature.talk.api.port;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;

import java.util.Optional;

public interface ReserveSeatPort {
  Optional<Talk> reserveSeat(Talk talk);
}
