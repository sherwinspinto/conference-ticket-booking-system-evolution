package com.sherwin.conference.bookingsystem.domain.feature.talk.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.CheckIfTalkExistsPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.CheckIfTalkExistsDbPort;

@DomainService
public class CheckIfTalkExistsService implements CheckIfTalkExistsPort {
  private final CheckIfTalkExistsDbPort checkIfTalkExistsDbPort;

  public CheckIfTalkExistsService(CheckIfTalkExistsDbPort checkIfTalkExistsDbPort) {
    this.checkIfTalkExistsDbPort = checkIfTalkExistsDbPort;
  }

  @Override
  public boolean checkIfTalkExists(TalkId talkId) {
    return checkIfTalkExistsDbPort.checkIfTalkExists(talkId);
  }
}
