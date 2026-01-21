package com.sherwin.conference.bookingsystem.domain.event.spi;

public interface DomainEventPublisherPort<T> {
  void publish(T event);
}
