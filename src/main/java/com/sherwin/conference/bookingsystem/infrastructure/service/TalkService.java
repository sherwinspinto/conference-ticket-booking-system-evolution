package com.sherwin.conference.bookingsystem.infrastructure.service;

import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.TalkDomainService;
import com.sherwin.conference.bookingsystem.domain.mapper.Mapper;
import com.sherwin.conference.bookingsystem.entity.TalkEntity;
import com.sherwin.conference.bookingsystem.infrastructure.repository.TalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TalkService {
  private final TalkDomainService talkDomainService;

  public TalkService(TalkDomainService talkDomainService) {
    this.talkDomainService = talkDomainService;
  }

  @Transactional
  public Talk create(String name, int totalSeats) {
    return talkDomainService.create(name, totalSeats);
  }
}
