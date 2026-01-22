package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.model.Version;

import java.util.Optional;

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
    TalkId id, Speaker speaker, String talkName, TalkTime talkTime, int seatCount, int reservedSeatCount, Version version) {
    return CreationResult.success(new Talk(id, speaker, new TalkName(talkName), talkTime, new SeatCount(seatCount), new ReservedSeatCount(reservedSeatCount), version));
  }

  public Optional<Talk> tryReserveSeat() {
    if (reservedSeats.value() < seatCount.value()) {
      return Optional.of(incrementReservedSeatCount());
    }
    return Optional.empty();
  }

  Talk incrementReservedSeatCount() {
    return new Talk(id, speaker, talkName, talkTime, seatCount, new ReservedSeatCount(reservedSeats.value() + 1), version);
  }
}
