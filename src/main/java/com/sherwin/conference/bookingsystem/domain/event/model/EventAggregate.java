package com.sherwin.conference.bookingsystem.domain.event.model;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.EventId;
import java.time.LocalDateTime;

public record EventAggregate(
    EventId id,
    Id.AggregateId aggregateId,
    Status status,
    ConferenceApplicationEvent.EventType eventType,
    ConferenceApplicationEvent event,
    CreatedAt createdAt,
    LastAttemptAt lastAttemptAt,
    RetryCount retryCount) {
  public enum Status {
    PENDING,
    SUCCESS,
    FAILURE;
  }

  public static final record CreatedAt(LocalDateTime createdAt) {}

  public static final record LastAttemptAt(LocalDateTime lastAttemptAt) {}

  public static final record RetryCount(int retryCount) {}
}
