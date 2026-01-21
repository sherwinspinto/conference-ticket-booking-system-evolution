package com.sherwin.conference.bookingsystem.infrastructure.event;

public interface DomainEventListenerPort<T> {
  void onEvent(T event);
}
