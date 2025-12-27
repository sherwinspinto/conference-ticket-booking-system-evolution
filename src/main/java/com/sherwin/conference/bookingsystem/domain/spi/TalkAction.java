package com.sherwin.conference.bookingsystem.domain.spi;

import com.sherwin.conference.bookingsystem.domain.Talk;

public interface TalkAction {
  Talk save(Talk talk);
}
