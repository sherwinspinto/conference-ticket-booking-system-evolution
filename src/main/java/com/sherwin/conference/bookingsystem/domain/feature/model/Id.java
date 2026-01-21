package com.sherwin.conference.bookingsystem.domain.feature.model;

public sealed interface Id<T> {
  T value();

  default boolean isPresent() {
    return value() != null;
  }

  record TalkId(Long value) implements Id<Long> {
   public TalkId {
     if (value == null || value < 0) {
       throw new IllegalArgumentException("TalkId cannot be null");
     }
   }
  }

  record TicketId(Long value) implements Id<Long> {
    public TicketId {
      if (value == null || value < 0) {
        throw new IllegalArgumentException("TicketId cannot be null");
      }
    }
  }

  record EventId(Long value) implements Id<Long> {
    public EventId {
      if (value == null || value < 0) {
        throw new IllegalArgumentException("EventId cannot be null");
      }
    }
  }

  record AggregateId(Long value) {
    public AggregateId {
      if (value == null || value < 0) {
        throw new IllegalArgumentException("AggregateId cannot be null");
      }
    }

    public static AggregateId of(String id) {
      return new AggregateId(Long.parseLong(id));
    }
  }
}
