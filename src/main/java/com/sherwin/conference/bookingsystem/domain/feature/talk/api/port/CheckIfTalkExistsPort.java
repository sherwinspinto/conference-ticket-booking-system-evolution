package com.sherwin.conference.bookingsystem.domain.feature.talk.api.port;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;

public interface CheckIfTalkExistsPort {
  boolean checkIfTalkExists(TalkId talkId);
}
