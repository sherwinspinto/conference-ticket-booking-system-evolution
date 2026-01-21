package com.sherwin.conference.bookingsystem.domain.feature.ticket.model;

public enum ReservationStatus {
  RESERVED("RESERVED"),
  CONFIRMED("CONFIRMED"),
  EXPIRED("EXPIRED");

  private final String value;

  ReservationStatus(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }
}
