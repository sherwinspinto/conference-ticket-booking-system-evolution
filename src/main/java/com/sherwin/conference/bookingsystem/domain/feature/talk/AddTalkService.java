package com.sherwin.conference.bookingsystem.domain.feature.talk;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.AddTalkPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.AddTalk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.AddTalkDbPort;

@DomainService
public class AddTalkService implements AddTalkPort {
  private final AddTalkDbPort addTalkDbPort;

  public AddTalkService(AddTalkDbPort addTalkDbPort) {
    this.addTalkDbPort = addTalkDbPort;
  }

  @Override
  public void addTalk(AddTalk addTalk) {

  }
}
