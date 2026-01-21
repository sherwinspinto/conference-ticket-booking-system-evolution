package com.sherwin.conference.bookingsystem.infrastructure.db.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.ReserveSeatDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserveSeatJPA implements ReserveSeatDbPort {
  private final TalkRepository talkRepository;

  @Autowired
  public ReserveSeatJPA(TalkRepository talkRepository) {
    this.talkRepository = talkRepository;
  }

  @Override
  public boolean reserveSeat(TalkId talkId) {
    return talkRepository.incrementReservedSeatCount(talkId.value()) == 1;
  }
}
