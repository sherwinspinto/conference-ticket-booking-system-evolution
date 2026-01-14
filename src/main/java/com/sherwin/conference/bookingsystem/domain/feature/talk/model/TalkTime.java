package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import java.time.LocalDateTime;

public record TalkTime(LocalDateTime startTime, LocalDateTime endTime) {}
