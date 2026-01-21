package com.sherwin.conference.bookingsystem.infrastructure.controller.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.AddTalkPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.AddTalk;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.AddTalkDto;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.TalkDto;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class AddTalkController {
  private final AddTalkPort addTalkPort;

  @Autowired
  public AddTalkController(AddTalkPort addTalkPort) {
    this.addTalkPort = addTalkPort;
  }

  @PostMapping("/talks")
  public ResponseEntity<?> addTalk(@RequestBody AddTalkDto dto) {
    var speaker = dto.toSpeaker();
    var talkTime = dto.toTalkTime();

    var result = AddTalk.of(speaker, dto.talkName(), talkTime, dto.seatCount());

    return switch (result) {
      case CreationResult.Success<AddTalk> success -> {
        TalkDto createdTalk = TalkDto.fromDomain(addTalkPort.addTalk(success.value()));

        URI location =
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTalk.id())
                .toUri();

        yield ResponseEntity.created(location).body(createdTalk);
      }
      case CreationResult.Failure(var errors) -> ResponseEntity.badRequest().body(errors);
    };
  }
}
