package com.sherwin.conference.bookingsystem.domain.feature.ticket.model;

public sealed interface TicketModel permits ReserveTicket, Ticket {}
