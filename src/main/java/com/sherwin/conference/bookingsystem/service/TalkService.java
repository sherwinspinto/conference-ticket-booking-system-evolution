package com.sherwin.conference.bookingsystem.service;

import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.mapper.Mapper;
import com.sherwin.conference.bookingsystem.entity.TalkEntity;
import com.sherwin.conference.bookingsystem.repository.TalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TalkService {
  private final TalkRepository talkRepository;

  @Autowired
  public TalkService(TalkRepository talkRepository) {
    this.talkRepository = talkRepository;
  }

  @Transactional
  public Talk create(String name, int totalSeats) {
    if (Objects.isNull(name) || name.isBlank())
      throw new IllegalArgumentException("Name cannot be null/blank");
    if (totalSeats < 1)
      throw new IllegalArgumentException("Total Seats must be greater than 0");

    TalkEntity talk = TalkEntity.createWithNameAndTotalSeats(name, totalSeats);
    TalkEntity talkEntity = talkRepository.save(talk);
    return Mapper.talkEntityToDomainMapper(talkEntity);
  }
}
