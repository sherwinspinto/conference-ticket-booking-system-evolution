package com.sherwin.conference.bookingsystem.infrastructure.controller.talk.controller;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.GetTalkPort;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.TalkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/talks")
public class GetTalkController {
  private final GetTalkPort getTalkPort;

  @Autowired
  public GetTalkController(GetTalkPort getTalkPort) {
    this.getTalkPort = getTalkPort;
  }

  @GetMapping("/{id}")
  public ResponseEntity<TalkDto> getTalk(@PathVariable Long id) {
    return getTalkPort
        .getTalkById(new TalkId(id))
        .map(talk -> ResponseEntity.ok(TalkDto.fromDomain(talk))) // Success: 200 OK
        .orElseGet(() -> ResponseEntity.notFound().build()); // Failure: 404 Not Found
  }
}
