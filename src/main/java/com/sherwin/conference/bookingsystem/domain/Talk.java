package com.sherwin.conference.bookingsystem.domain;

import java.util.Objects;

public record Talk(Long id, String name, int totalSeats, int reservedSeats, Long version) {
  public Talk (String name, int totalSeats) {
    this (null, name, totalSeats, 0, null);
  }
  public boolean tryReserveSeat() {
    if (reservedSeats >= totalSeats) return Boolean.FALSE;
    return Boolean.TRUE;
  }
}
