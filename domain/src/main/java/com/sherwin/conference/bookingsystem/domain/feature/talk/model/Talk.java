package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.model.Version;

import static com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.*;

public record Talk(
    TalkId id,
    Speaker speaker,
    TalkName talkName,
    TalkTime talkTime,
    SeatCount seatCount,
    ReservedSeatCount reservedSeats,
    Version version)
    implements TalkModel {
  public static CreationResult<Talk> of(
    TalkId id, Speaker speaker, String talkName, TalkTime talkTime, int seatCount, Version version) {
    return CreationResult.success(new Talk(id, speaker, new TalkName(talkName), talkTime, new SeatCount(seatCount), new ReservedSeatCount(0), version));
  }
}
