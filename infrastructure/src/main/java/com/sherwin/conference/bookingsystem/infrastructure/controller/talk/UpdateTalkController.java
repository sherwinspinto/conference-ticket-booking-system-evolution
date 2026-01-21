package com.sherwin.conference.bookingsystem.infrastructure.controller.talk;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.UpdateTalkPort;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.TalkDto;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.UpdateTalkDto;
import java.net.URI;
import java.util.List;

import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.UpdateTalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/talks")
public class UpdateTalkController {
  private final UpdateTalkPort updateTalkPort;

  @Autowired
  public UpdateTalkController(UpdateTalkPort updateTalkPort) {
    this.updateTalkPort = updateTalkPort;
  }

  @PutMapping("/{talkId}")
  public ResponseEntity<?> updateTalk(
      @RequestBody UpdateTalkDto updateTalkDto, @PathVariable Long talkId) {
    CreationResult<UpdateTalk> creationResult =
        UpdateTalk.of(
            updateTalkDto.id(),
            updateTalkDto.talkName(),
            updateTalkDto.toSpeaker(),
            updateTalkDto.toTalkTime(),
            updateTalkDto.seatCount(),
            updateTalkDto.version());

    return switch (creationResult) {
      case CreationResult.Success<UpdateTalk>(UpdateTalk updateTalk) -> {
        Talk updatedTalk = updateTalkPort.updateTalk(new TalkId(talkId), updateTalk);
        TalkDto talkDto = TalkDto.fromDomain(updatedTalk);
        URI location =
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(talkDto.id())
                .toUri();
        yield ResponseEntity.ok().location(location).body(talkDto);
      }
      case CreationResult.Failure<UpdateTalk>(List<FieldError> validationErrors) ->
          ResponseEntity.badRequest().body(validationErrors);
    };
  }
}
