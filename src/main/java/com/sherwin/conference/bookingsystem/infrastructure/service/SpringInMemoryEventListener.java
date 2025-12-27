package com.sherwin.conference.bookingsystem.infrastructure.service;

import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.feature.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class SpringInMemoryEventListener {
  private final EventHandler eventHandler;

  @Autowired
  public SpringInMemoryEventListener(EventHandler eventHandler) {
    this.eventHandler = eventHandler;
  }

  @TransactionalEventListener
  public void handleEvent(GenericSpringEvent<ConferenceApplicationEvent> event) {
    eventHandler.handleEvent(event.what());
  }
}
