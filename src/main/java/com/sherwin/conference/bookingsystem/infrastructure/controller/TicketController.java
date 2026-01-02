package com.sherwin.conference.bookingsystem.infrastructure.controller;

import com.sherwin.conference.bookingsystem.infrastructure.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
  @Autowired private TicketService ticketService;

  @PostMapping("/reserve")
  public String reserve(@RequestParam Long talkId, @RequestParam String userEmail) {
    return ticketService.reserveTicket(talkId, userEmail);
  }

  @PostMapping("/pay/{ticketId}")
  public String pay(@PathVariable Long ticketId) {
    return ticketService.confirmPayment(ticketId);
  }
}
