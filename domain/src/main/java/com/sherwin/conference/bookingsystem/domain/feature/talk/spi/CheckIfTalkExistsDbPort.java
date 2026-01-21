package com.sherwin.conference.bookingsystem.domain.feature.talk.spi;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;

public interface CheckIfTalkExistsDbPort {
  boolean checkIfTalkExists(TalkId talkId);
}
