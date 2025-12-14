package com.sherwin.conference.bookingsystem.domain;

public sealed interface ReservationResult {
  String RESERVED = "RESERVED";
  String PAID = "PAID";
  String EXPIRED = "EXPIRED";

  record Reserved() implements ReservationResult {
    @Override
    public String toString() {
      return RESERVED;
    }
  }

  record Paid() implements ReservationResult {
    @Override
    public String toString() {
      return PAID;
    }
  }

  record Expired() implements ReservationResult {
    @Override
    public String toString() {
      return EXPIRED;
    }
  }
}
