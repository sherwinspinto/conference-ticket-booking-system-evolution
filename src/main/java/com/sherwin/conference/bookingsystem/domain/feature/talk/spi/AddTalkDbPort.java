package com.sherwin.conference.bookingsystem.domain.feature.talk.spi;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.AddTalk;

public interface AddTalkDbPort {
  long addTalk(AddTalk addTalk);
}
