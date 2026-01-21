package com.sherwin.conference.bookingsystem.infrastructure.db.service.talk;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.AddTalk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.AddTalkDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddTalkJPA implements AddTalkDbPort {
  private final TalkRepository talkRepository;

  @Autowired
  public AddTalkJPA(TalkRepository talkRepository) {
    this.talkRepository = talkRepository;
  }

  @Override
  public Talk addTalk(AddTalk addTalk) {
    return TalkEntity.toDomain(talkRepository.save(TalkEntity.from(addTalk)));
  }
}
