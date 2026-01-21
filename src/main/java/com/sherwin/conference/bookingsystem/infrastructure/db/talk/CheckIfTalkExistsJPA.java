package com.sherwin.conference.bookingsystem.infrastructure.db.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.CheckIfTalkExistsDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository2;
import org.springframework.stereotype.Service;

@Service
public class CheckIfTalkExistsJPA implements CheckIfTalkExistsDbPort {
  private final TalkRepository2 talkRepository;

  public CheckIfTalkExistsJPA(TalkRepository2 talkRepository) {
    this.talkRepository = talkRepository;
  }

  @Override
  public boolean checkIfTalkExists(TalkId talkId) {
    return talkRepository.existsById(talkId.value());
  }
}
