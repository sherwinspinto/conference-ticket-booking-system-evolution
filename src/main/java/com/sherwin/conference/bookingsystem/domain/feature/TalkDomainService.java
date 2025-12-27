package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.spi.TalkAction;
import java.util.Objects;

@DomainService
public class TalkDomainService {
  private final TalkAction talkAction;

  public TalkDomainService(TalkAction talkAction) {
    this.talkAction = talkAction;
  }

  public Talk create(String name, int totalSeats) {
    if (Objects.isNull(name) || name.isBlank())
      throw new IllegalArgumentException("Name cannot be null/blank");
    if (totalSeats < 1) throw new IllegalArgumentException("Total Seats must be greater than 0");

    return talkAction.save(new Talk(name, totalSeats));
  }
}
