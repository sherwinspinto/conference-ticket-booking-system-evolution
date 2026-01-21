package com.sherwin.conference.bookingsystem.infrastructure.db.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.GetTalkDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository2;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetTalkJPA implements GetTalkDbPort {
  private final TalkRepository2 talkRepository2;

  @Autowired
  public GetTalkJPA(TalkRepository2 talkRepository2) {
    this.talkRepository2 = talkRepository2;
  }

  @Override
  public Optional<Talk> getTalkById(TalkId talkId) {
    return talkRepository2.findById(talkId.value()).map(TalkEntity::toDomain);
  }
}
