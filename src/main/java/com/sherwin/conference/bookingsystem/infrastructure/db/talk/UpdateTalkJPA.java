package com.sherwin.conference.bookingsystem.infrastructure.db.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.UpdateTalk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.spi.UpdateTalkDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TalkRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateTalkJPA implements UpdateTalkDbPort {
  private final TalkRepository2 talkRepository;

  @Autowired
  public UpdateTalkJPA(TalkRepository2 talkRepository) {
    this.talkRepository = talkRepository;
  }

  @Override
  @Transactional
  public Talk updateTalk(TalkId talkId, UpdateTalk talk) {
    TalkEntity existingTalkEntity =
        talkRepository
            .findById(talkId.value())
            .orElseThrow(() -> new IllegalArgumentException("Talk not found"));

    TalkEntity updatedEntity = TalkEntity.from(talk, existingTalkEntity.getReservedSeats());

    return TalkEntity.toDomain(talkRepository.save(updatedEntity));
  }
}
