package com.sherwin.conference.bookingsystem.domain.feature.talk.api.port;

import com.sherwin.conference.bookingsystem.domain.feature.model.PageRequest;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;

import java.util.List;

public interface GetTalksPort {
  List<Talk> getAllTalks(PageRequest pageRequest);
}
