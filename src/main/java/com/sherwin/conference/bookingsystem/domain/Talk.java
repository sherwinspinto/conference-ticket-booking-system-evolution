package com.sherwin.conference.bookingsystem.domain;

public record Talk(Long id, String name, int totalSeats, int reservedSeats) {}
