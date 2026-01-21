package com.sherwin.conference.bookingsystem.domain.event.model;

public record HandlerResult(boolean success, String message, Throwable error) {

  public static HandlerResult success(String message) {
    return new HandlerResult(true, message, null);
  }

  public static HandlerResult failure(String message, Throwable error) {
    return new HandlerResult(false, message, error);
  }
}
