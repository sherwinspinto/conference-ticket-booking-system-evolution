package com.sherwin.conference.bookingsystem.infrastructure.db.service.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.GetTalkDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetTalkJPA implements GetTalkDbPort {
  private final TalkRepository talkRepository;

  @Autowired
  public GetTalkJPA(TalkRepository talkRepository) {
    this.talkRepository = talkRepository;
  }

  @Override
  public Optional<Talk> getTalkById(TalkId talkId) {
    return talkRepository.findById(talkId.value()).map(TalkEntity::toDomain);
  }
}
