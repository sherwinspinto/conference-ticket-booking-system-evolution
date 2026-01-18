package com.sherwin.conference.bookingsystem.domain.feature.talk.api.port;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.AddTalk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;

public interface AddTalkPort {
  Talk addTalk(AddTalk addTalk);
}
