package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent;

public interface EventHandler {
  boolean handleEvent(ConferenceApplicationEvent event);
}
