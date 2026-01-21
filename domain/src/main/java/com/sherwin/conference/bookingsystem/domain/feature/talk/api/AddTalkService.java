package com.sherwin.conference.bookingsystem.domain.feature.talk.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.AddTalkPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.AddTalk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.AddTalkDbPort;

@DomainService
public class AddTalkService implements AddTalkPort {
  private final AddTalkDbPort addTalkDbPort;

  public AddTalkService(AddTalkDbPort addTalkDbPort) {
    this.addTalkDbPort = addTalkDbPort;
  }

  @Override
  public Talk addTalk(AddTalk addTalk) {
    return addTalkDbPort.addTalk(addTalk);
  }
}
