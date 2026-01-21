package com.sherwin.conference.bookingsystem.infrastructure.db.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.PageRequest;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.GetTalksDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllTalksJPA implements GetTalksDbPort {
  private final TalkRepository talkRepository;

  @Autowired
  GetAllTalksJPA(TalkRepository talkRepository) {
    this.talkRepository = talkRepository;
  }

  @Override
  public List<Talk> getAllTalks(PageRequest domainRequest) {
    var springPageable = org.springframework.data.domain.PageRequest.of(
      domainRequest.page(),
      domainRequest.size()
    );
    return talkRepository.findAll(springPageable).map(TalkEntity::toDomain).toList();
  }
}
