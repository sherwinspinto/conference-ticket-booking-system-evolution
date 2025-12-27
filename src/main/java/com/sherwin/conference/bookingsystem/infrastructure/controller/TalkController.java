package com.sherwin.conference.bookingsystem.infrastructure.controller;

import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.infrastructure.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/talks")
public class TalkController {
  private final TalkService talkService;

  @Autowired
  public TalkController(TalkService talkService) {
    this.talkService = talkService;
  }

  @PostMapping("/create")
  public String create(@RequestParam String name, @RequestParam int totalSeats) {
    Talk talk = talkService.create(name, totalSeats);
    return String.format("New Talk: %s created with Id: %d", talk.name(), talk.id());
  }
}
