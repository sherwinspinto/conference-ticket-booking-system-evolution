package com.sherwin.conference.bookingsystem.infrastructure.service;

import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;

public record GenericSpringEvent<T extends ConferenceApplicationEvent>(
    T what, boolean success) {}
