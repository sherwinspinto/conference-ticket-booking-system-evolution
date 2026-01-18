package com.sherwin.conference.bookingsystem.domain.feature.model;

public sealed interface Id<T> {
  T value();

  default boolean isPresent() {
    return value() != null;
  }

  record TalkId(Long value) implements Id<Long> {
   public TalkId {
     if (value == null) {
       throw new IllegalArgumentException("TalkId cannot be null");
     }
   }
  }
}
