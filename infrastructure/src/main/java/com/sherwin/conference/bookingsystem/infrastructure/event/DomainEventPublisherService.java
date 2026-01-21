package com.sherwin.conference.bookingsystem.infrastructure.event;

import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.event.spi.DomainEventPublisherPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DomainEventPublisherService
    implements DomainEventPublisherPort<ConferenceApplicationEvent> {
  private final ApplicationEventPublisher publisher;

  public DomainEventPublisherService(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public void publish(ConferenceApplicationEvent event) {
    publisher.publishEvent(event);
  }
}
