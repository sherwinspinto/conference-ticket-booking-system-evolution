package com.sherwin.conference.bookingsystem.domain.event.api.port;

public interface DomainEventHandlerPort<T> {
  void handle(T event);
}
