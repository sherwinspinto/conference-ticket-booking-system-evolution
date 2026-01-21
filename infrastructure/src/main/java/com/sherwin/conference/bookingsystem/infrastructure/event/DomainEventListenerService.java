package com.sherwin.conference.bookingsystem.infrastructure.event;

import com.sherwin.conference.bookingsystem.domain.event.api.port.DomainEventHandlerPort;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class DomainEventListenerService
    implements DomainEventListenerPort<ConferenceApplicationEvent> {
  private final DomainEventHandlerPort<ConferenceApplicationEvent> delegate;

  @Autowired
  public DomainEventListenerService(DomainEventHandlerPort<ConferenceApplicationEvent> delegate) {
    this.delegate = delegate;
  }

  @Override
  @TransactionalEventListener
  public void onEvent(ConferenceApplicationEvent event) {
    delegate.handle(event);
  }
}
