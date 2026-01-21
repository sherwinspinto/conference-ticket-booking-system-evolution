package com.sherwin.conference.bookingsystem.infrastructure.db.service.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.CheckIfTalkExistsDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckIfTalkExistsJPA implements CheckIfTalkExistsDbPort {
  private final TalkRepository talkRepository;

  public CheckIfTalkExistsJPA(TalkRepository talkRepository) {
    this.talkRepository = talkRepository;
  }

  @Override
  public boolean checkIfTalkExists(TalkId talkId) {
    return talkRepository.existsById(talkId.value());
  }
}
