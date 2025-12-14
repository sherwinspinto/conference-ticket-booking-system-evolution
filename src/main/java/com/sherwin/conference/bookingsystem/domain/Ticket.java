package com.sherwin.conference.bookingsystem.domain;

import java.time.LocalDateTime;

public record Ticket(
    Long id,
    Long talkId,
    String userEmail,
    ReservationResult status,
    LocalDateTime reservedAt,
    LocalDateTime expiresAt) {}
