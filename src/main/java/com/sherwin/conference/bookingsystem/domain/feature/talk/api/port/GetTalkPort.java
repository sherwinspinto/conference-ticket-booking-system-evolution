package com.sherwin.conference.bookingsystem.domain.feature.talk.api.port;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import java.util.Optional;

public interface GetTalkPort {
  Optional<Talk> getTalkById(TalkId talkId);
}
