package com.sherwin.conference.bookingsystem.infrastructure.repository;

import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.mapper.Mapper;
import com.sherwin.conference.bookingsystem.domain.spi.TalkAction;
import org.springframework.stereotype.Service;

@Service
public class TalkActionRepository implements TalkAction {
  private final TalkRepository talkRepository;

  public TalkActionRepository(TalkRepository talkRepository) {
    this.talkRepository = talkRepository;
  }

  @Override
  public Talk save(Talk talk) {
    return Mapper.talkEntityToDomainMapper(
        talkRepository.save(Mapper.talkDomainToEntityMapper(talk)));
  }
}
