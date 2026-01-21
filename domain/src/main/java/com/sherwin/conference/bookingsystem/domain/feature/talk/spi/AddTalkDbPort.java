package com.sherwin.conference.bookingsystem.domain.feature.talk.spi;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.AddTalk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;

public interface AddTalkDbPort {
  Talk addTalk(AddTalk addTalk);
}
