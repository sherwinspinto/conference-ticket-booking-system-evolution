package com.sherwin.conference.bookingsystem.controller;

import com.sherwin.conference.bookingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
  @Autowired
  private TicketService ticketService;

  @PostMapping("/reserve")
  public String reserve(@RequestParam Long talkId, @RequestParam String userEmail) {
    return ticketService.reserveTicket(talkId, userEmail);
  }

  @PostMapping("/pay/{ticketId}")
  public String pay(@PathVariable Long ticketId) {
    return ticketService.confirmPayment(ticketId);
  }

  @GetMapping("/expire")
  public String expire() { // Manual trigger for testing
    ticketService.expireOldReservations();
    return "Expired old reservations";
  }
}
