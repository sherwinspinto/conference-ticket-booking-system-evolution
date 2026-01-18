package com.sherwin.conference.bookingsystem.infrastructure.db.entity;

import java.time.LocalDateTime;

public record TalkTimeJson(LocalDateTime startTime, LocalDateTime endTime) {}
