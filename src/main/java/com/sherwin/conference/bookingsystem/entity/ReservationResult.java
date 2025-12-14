package com.sherwin.conference.bookingsystem.entity;


public sealed interface ReservationResult
    permits ReservationResult.Reserved, ReservationResult.Paid, ReservationResult.Expired {
  String RESERVED = "RESERVED";
  String PAID = "PAID";
  String EXPIRED = "EXPIRED";

  static ReservationResult fromDbValue(String dbValue) {
    if (RESERVED.equals(dbValue)) return new Reserved();
    else if (PAID.equals(dbValue)) return new Paid();
    else if (EXPIRED.equals(dbValue)) return new Expired();
    else throw new IllegalArgumentException("Invalid Ticket Status");
  }

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
