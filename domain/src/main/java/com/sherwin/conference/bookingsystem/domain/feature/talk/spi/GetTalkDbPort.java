package com.sherwin.conference.bookingsystem.domain.feature.talk.spi;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import java.util.Optional;

public interface GetTalkDbPort {
  Optional<Talk> getTalkById(TalkId talkId);
}
