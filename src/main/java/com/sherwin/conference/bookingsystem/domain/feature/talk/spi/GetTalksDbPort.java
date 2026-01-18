package com.sherwin.conference.bookingsystem.domain.feature.talk.spi;

import com.sherwin.conference.bookingsystem.domain.feature.model.PageRequest;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;

import java.util.List;

public interface GetTalksDbPort {
  List<Talk> getAllTalks(PageRequest domainPageRequest);
}
