package com.sherwin.conference.bookingsystem.infrastructure.db.talk;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.AddTalk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.AddTalkDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddTalkJPA implements AddTalkDbPort {
  private final TalkRepository2 talkRepository2;

  @Autowired
  public AddTalkJPA(TalkRepository2 talkRepository2) {
    this.talkRepository2 = talkRepository2;
  }

  @Override
  public Talk addTalk(AddTalk addTalk) {
    return TalkEntity.toDomain(talkRepository2.save(TalkEntity.from(addTalk)));
  }
}
