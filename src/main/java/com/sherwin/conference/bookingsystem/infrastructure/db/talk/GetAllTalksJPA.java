package com.sherwin.conference.bookingsystem.infrastructure.db.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.PageRequest;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.GetTalksDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllTalksJPA implements GetTalksDbPort {
  private final TalkRepository2 talkRepository2;

  @Autowired
  GetAllTalksJPA(TalkRepository2 talkRepository2) {
    this.talkRepository2 = talkRepository2;
  }

  @Override
  public List<Talk> getAllTalks(PageRequest domainRequest) {
    var springPageable = org.springframework.data.domain.PageRequest.of(
      domainRequest.page(),
      domainRequest.size()
    );
    return talkRepository2.findAll(springPageable).map(TalkEntity::toDomain).toList();
  }
}
