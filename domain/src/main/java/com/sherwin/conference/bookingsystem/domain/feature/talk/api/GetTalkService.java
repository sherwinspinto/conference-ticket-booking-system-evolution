package com.sherwin.conference.bookingsystem.domain.feature.talk.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.GetTalkPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.GetTalkDbPort;
import java.util.Optional;

@DomainService
public class GetTalkService implements GetTalkPort {
  private final GetTalkDbPort getTalkDbPort;

  public GetTalkService(GetTalkDbPort getTalkDbPort) {
    this.getTalkDbPort = getTalkDbPort;
  }

  @Override
  public Optional<Talk> getTalkById(TalkId talkId) {
    return getTalkDbPort.getTalkById(talkId);
  }
}
