package com.sherwin.conference.bookingsystem.domain.feature.talk.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.UpdateTalkPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.UpdateTalk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.UpdateTalkDbPort;

@DomainService
public class UpdateTalkService implements UpdateTalkPort {
  private final UpdateTalkDbPort updateTalkDbPort;

  public UpdateTalkService(UpdateTalkDbPort updateTalkDbPort) {
    this.updateTalkDbPort = updateTalkDbPort;
  }

  public Talk updateTalk(TalkId talkId, UpdateTalk updateTalk) {
    return updateTalkDbPort.updateTalk(talkId, updateTalk);
  }
}
