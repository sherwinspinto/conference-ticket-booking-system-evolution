package com.sherwin.conference.bookingsystem.infrastructure.dto.talk;

import java.time.LocalDateTime;

public record TalkTimeDto(LocalDateTime startTime, LocalDateTime endTime) {}
