package com.sherwin.conference.bookingsystem.infrastructure.db.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sherwin.conference.bookingsystem.domain.event.model.EventAggregate;
import com.sherwin.conference.bookingsystem.domain.event.model.EventCommand;
import com.sherwin.conference.bookingsystem.domain.event.spi.SaveReserveSeatEventDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.event.OutboxEventEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.OutboxEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveReserveSeatEventJPA implements SaveReserveSeatEventDbPort {
  private final OutboxEventRepository outboxEventRepository;
  private final ObjectMapper objectMapper;

  @Autowired
  public SaveReserveSeatEventJPA(OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {
    this.outboxEventRepository = outboxEventRepository;
    this.objectMapper = objectMapper;
  }
  @Override
  public EventAggregate saveReserveSeatEvent(EventCommand.SaveReserveTicketEvent saveReserveTicketEvent) {
    try {
      OutboxEventEntity outboxEventEntity = OutboxEventEntity.fromDomain(saveReserveTicketEvent, objectMapper);
      return OutboxEventEntity.toDomain(outboxEventRepository.save(outboxEventEntity), objectMapper);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
