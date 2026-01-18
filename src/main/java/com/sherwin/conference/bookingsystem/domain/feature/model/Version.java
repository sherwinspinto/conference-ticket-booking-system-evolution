package com.sherwin.conference.bookingsystem.domain.feature.model;

public record Version(Long value) {
  public Version {
    if (value == null || value < 0) {
      throw new IllegalArgumentException("Version must be greater than 0");
    }
  }
  public static Version of(Long version) {
    return new Version(version);
  }
}
