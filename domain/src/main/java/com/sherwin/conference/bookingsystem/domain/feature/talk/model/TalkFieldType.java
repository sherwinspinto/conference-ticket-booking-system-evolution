package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import java.time.LocalDateTime;

public sealed interface TalkFieldType<T> {
  T value();
  record FirstName(String value) implements TalkFieldType<String> {
    public FirstName {
      if (value == null) {
        throw new IllegalArgumentException("First name cannot be null");
      }
    }
  }

  record LastName(String value) implements TalkFieldType<String> {
    public LastName {
      if (value == null) {
        throw new IllegalArgumentException("Last name cannot be null");
      }
    }
  }

  record SeatCount(Integer value) implements TalkFieldType<Integer> {
    public SeatCount {
      if (value == null || value < 1) {
        throw new IllegalArgumentException("Seat count must be greater than 0");
      }
    }
  }

  record ReservedSeatCount(Integer value) implements TalkFieldType<Integer> {
    public ReservedSeatCount {
      if (value == null || value < 0) {
        throw new IllegalArgumentException("Reserved seat count must be greater than or equal to 0");
      }
    }
  }

  record TalkName(String value) implements TalkFieldType<String> {
    public TalkName {
      if (value == null || value.isBlank()) {
        throw new IllegalArgumentException("Talk name cannot be null/blank");
      }
    }
  }

  record StartTime(LocalDateTime value) implements TalkFieldType<LocalDateTime> {
    public StartTime {
      if (value == null) {
        throw new IllegalArgumentException("Start time cannot be null");
      }
    }
  }

  record EndTime(LocalDateTime value) implements TalkFieldType<LocalDateTime> {
    public EndTime {
      if (value == null) {
        throw new IllegalArgumentException("End time cannot be null");
      }
    }
  }
}
