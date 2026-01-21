package com.sherwin.conference.bookingsystem.infrastructure.controller.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.PageRequest;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.GetTalksPort;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.TalkDto;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/talks")
public class GetTalksController {
  private final GetTalksPort getTalksPort;

  public GetTalksController(GetTalksPort getTalksPort) {
    this.getTalksPort = getTalksPort;
  }

  @GetMapping("/")
  public ResponseEntity<List<TalkDto>> getAllTalks(Pageable pageable) {
    return ResponseEntity.ok(getTalksPort.getAllTalks(PageRequest.from(pageable)).stream().map(TalkDto::fromDomain).toList());
  }
}
