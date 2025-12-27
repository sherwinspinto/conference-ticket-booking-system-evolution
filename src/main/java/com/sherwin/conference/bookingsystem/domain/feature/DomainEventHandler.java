package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent;

@DomainService
public class DomainEventHandler implements EventHandler {
  @Override
  public boolean handleEvent(ConferenceApplicationEvent event) {
   return true;
  }
}
