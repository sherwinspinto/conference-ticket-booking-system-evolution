package com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk;

import java.time.LocalDateTime;

public record TalkTimeJson(LocalDateTime startTime, LocalDateTime endTime) {}
