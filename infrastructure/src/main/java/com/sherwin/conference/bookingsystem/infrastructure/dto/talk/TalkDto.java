package com.sherwin.conference.bookingsystem.infrastructure.dto.talk;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;

public record TalkDto(
    long id,
    String talkName,
    SpeakerDto speaker,
    TalkTimeDto talkTime,
    int seatCount,
    int reservedSeats,
    long version) {
  public static TalkDto of(
      long id,
      String talkName,
      SpeakerDto speaker,
      TalkTimeDto talkTime,
      int seatCount,
      int reservedSeats,
      long version) {
    return new TalkDto(id, talkName, speaker, talkTime, seatCount, reservedSeats, version);
  }

  public static TalkDto fromDomain(Talk talk) {
    return TalkDto.of(
        talk.id().value(),
        talk.talkName().value(),
        new SpeakerDto(talk.speaker().firstName().value(), talk.speaker().lastName().value()),
        new TalkTimeDto(talk.talkTime().startTime().value(), talk.talkTime().endTime().value()),
        talk.seatCount().value(),
        talk.reservedSeats().value(),
        talk.version().value());
  }
}
