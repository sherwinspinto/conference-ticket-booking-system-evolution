package com.sherwin.conference.bookingsystem.domain.feature.talk.spi;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;

public interface ReserveSeatDbPort {
  boolean reserveSeat(TalkId talkId);
}
