package com.sherwin.conference.bookingsystem.domain.feature.ticket.model;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.CommonValidations;

import java.time.LocalDateTime;

public interface TicketFieldType {
  record Email(String value) implements TicketFieldType {
    public Email {
      if (!CommonValidations.isValidEmail(value)) {
        throw new IllegalArgumentException("Email cannot be null");
      }
    }
  }

  record ReservedAt(LocalDateTime value) implements TicketFieldType {
    public ReservedAt {
      if (value == null) {
        throw new IllegalArgumentException("Reserved at cannot be null");
      }
    }
  }
}
