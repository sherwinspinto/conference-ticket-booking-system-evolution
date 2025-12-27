package com.sherwin.conference.bookingsystem.infrastructure.service;

import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.spi.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringInMemoryEventPublisher implements EventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public SpringInMemoryEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public Boolean publish(ConferenceApplicationEvent seatReserved, boolean success) {
    applicationEventPublisher.publishEvent(new GenericSpringEvent<ConferenceApplicationEvent>(seatReserved, success));
    return true;
  }
}
