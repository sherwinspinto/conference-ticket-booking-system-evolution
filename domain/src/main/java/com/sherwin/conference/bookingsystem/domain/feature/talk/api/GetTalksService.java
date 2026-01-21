package com.sherwin.conference.bookingsystem.domain.feature.talk.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.feature.model.PageRequest;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.GetTalksPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.GetTalksDbPort;

import java.util.List;

@DomainService
public class GetTalksService implements GetTalksPort {
  private final GetTalksDbPort getTalksDbPort;

  public GetTalksService(GetTalksDbPort getTalksDbPort) {
    this.getTalksDbPort = getTalksDbPort;
  }

  @Override
  public List<Talk> getAllTalks(PageRequest domainRequest) {
    return getTalksDbPort.getAllTalks(domainRequest);
  }
}
