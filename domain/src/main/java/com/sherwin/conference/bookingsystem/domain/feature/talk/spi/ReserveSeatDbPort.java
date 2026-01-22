package com.sherwin.conference.bookingsystem.domain.feature.talk.spi;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;

public interface ReserveSeatDbPort {
  Talk reserveSeat(Talk talk);
}
