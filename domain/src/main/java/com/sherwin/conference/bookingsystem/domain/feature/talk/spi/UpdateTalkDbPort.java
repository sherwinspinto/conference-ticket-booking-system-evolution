package com.sherwin.conference.bookingsystem.domain.feature.talk.spi;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.UpdateTalk;

public interface UpdateTalkDbPort {
  Talk updateTalk(TalkId talkId, UpdateTalk talk);
}
