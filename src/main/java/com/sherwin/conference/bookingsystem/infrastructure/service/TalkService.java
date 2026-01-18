package com.sherwin.conference.bookingsystem.infrastructure.service;

import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.TalkDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
