package com.sherwin.conference.bookingsystem.domain.feature.talk.api.port;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;

public interface ReserveSeatPort {
  boolean reserveSeat(TalkId talkId);
}
