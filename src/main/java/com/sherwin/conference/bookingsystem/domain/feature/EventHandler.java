package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;

public interface EventHandler {
  boolean handleEvent(ConferenceApplicationEvent event);
}
