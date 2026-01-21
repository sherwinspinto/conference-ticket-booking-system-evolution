package com.sherwin.conference.bookingsystem.domain.feature.model;

public record PageRequest(int page, int size) {
  public static PageRequest of(int page, int size) {
    return new PageRequest(Math.max(0, page), Math.max(0, size));
  }
}
