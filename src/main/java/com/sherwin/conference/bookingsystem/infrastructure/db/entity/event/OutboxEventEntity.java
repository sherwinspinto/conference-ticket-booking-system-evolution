package com.sherwin.conference.bookingsystem.infrastructure.db.entity.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.event.model.EventAggregate;
import com.sherwin.conference.bookingsystem.domain.event.model.EventCommand;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.AggregateId;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.EventId;
import com.sherwin.conference.bookingsystem.infrastructure.db.service.event.dto.Event;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
public class OutboxEventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String aggregateId;
  private String eventType;
  @Lob private String payload;
  private String status; // PENDING, PUBLISHED, FAILED
  private LocalDateTime createdAt;
  private LocalDateTime lastAttemptAt;
  private int retryCount;

  public OutboxEventEntity() {}

  public OutboxEventEntity(
      String aggregateId,
      String eventType,
      String payload,
      String status,
      LocalDateTime createdAt) {
    this.aggregateId = aggregateId;
    this.eventType = eventType;
    this.payload = payload;
    this.status = status;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAggregateId() {
    return aggregateId;
  }

  public void setAggregateId(String aggregateId) {
    this.aggregateId = aggregateId;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getLastAttemptAt() {
    return lastAttemptAt;
  }

  public void setLastAttemptAt(LocalDateTime lastAttemptAt) {
    this.lastAttemptAt = lastAttemptAt;
  }

  public int getRetryCount() {
    return retryCount;
  }

  public void setRetryCount(int retryCount) {
    this.retryCount = retryCount;
  }

  public static OutboxEventEntity fromDomain(
      EventCommand.SaveReserveTicketEvent saveReserveTicketEvent, ObjectMapper objectMapper)
      throws JsonProcessingException {
    return new OutboxEventEntity(
        saveReserveTicketEvent.ticketId().value().toString(),
        saveReserveTicketEvent.event().eventType().name(),
      objectMapper.writeValueAsString(Event.fromDomain(saveReserveTicketEvent.event())),
        saveReserveTicketEvent.status().name(),
        saveReserveTicketEvent.createdAt().createdAt());
  }

  public static EventAggregate toDomain(
      OutboxEventEntity outboxEventEntity, ObjectMapper objectMapper)
      throws JsonProcessingException {
    EventAggregate.Status status = EventAggregate.Status.valueOf(outboxEventEntity.status);
    ConferenceApplicationEvent.EventType eventType =
        ConferenceApplicationEvent.EventType.valueOf(outboxEventEntity.eventType);
    ConferenceApplicationEvent event =
        Event.toDomain(objectMapper.readValue(outboxEventEntity.payload, Event.class));

    return new EventAggregate(
        new EventId(outboxEventEntity.id),
        AggregateId.of(outboxEventEntity.aggregateId),
        status,
        eventType,
        event,
        new EventAggregate.CreatedAt(outboxEventEntity.createdAt),
        new EventAggregate.LastAttemptAt(outboxEventEntity.lastAttemptAt),
        new EventAggregate.RetryCount(outboxEventEntity.retryCount));
  }
}
