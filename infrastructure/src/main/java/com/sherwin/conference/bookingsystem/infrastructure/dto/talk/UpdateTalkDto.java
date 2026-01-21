package com.sherwin.conference.bookingsystem.infrastructure.dto.talk;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Speaker;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.EndTime;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.FirstName;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.LastName;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.StartTime;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkTime;

public record UpdateTalkDto(
    long id,
    String talkName,
    SpeakerDto speaker,
    TalkTimeDto talkTime,
    int seatCount,
    long version) {
  public static UpdateTalkDto of(
      long id,
      String talkName,
      SpeakerDto speaker,
      TalkTimeDto talkTime,
      int seatCount,
      long version) {
    return new UpdateTalkDto(id, talkName, speaker, talkTime, seatCount, version);
  }

  public Speaker toSpeaker() {
    return new Speaker(new FirstName(speaker.firstName()), new LastName(speaker.lastName()));
  }

  public TalkTime toTalkTime() {
    return new TalkTime(new StartTime(talkTime.startTime()), new EndTime(talkTime.endTime()));
  }
}
