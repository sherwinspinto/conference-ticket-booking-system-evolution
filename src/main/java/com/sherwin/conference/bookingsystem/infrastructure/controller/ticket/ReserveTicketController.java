package com.sherwin.conference.bookingsystem.infrastructure.controller.ticket;

import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port.ReserveTicketPort;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.ReserveTicketInput;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.ReserveTicketResult;
import com.sherwin.conference.bookingsystem.infrastructure.dto.ticket.ReserveTicketDto;
import com.sherwin.conference.bookingsystem.infrastructure.dto.ticket.TicketDto;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ReserveTicketController {
  private final ReserveTicketPort reserveTicketPort;

  public ReserveTicketController(ReserveTicketPort reserveTicketPort) {
    this.reserveTicketPort = reserveTicketPort;
  }

  @PostMapping("/tickets/talks/{talkId}/reserve")
  @Transactional
  public ResponseEntity<?> reserveTicket(
      @RequestBody ReserveTicketDto reserveTicketDto, @PathVariable("talkId") Long talkId) {
    CreationResult<ReserveTicketInput> creationResult =
        ReserveTicketInput.of(talkId, reserveTicketDto.userEmail());
    return switch (creationResult) {
      case CreationResult.Success<ReserveTicketInput> success -> {
        var reserveTicketResult = reserveTicketPort.reserveTicket(success.value());
        yield switch (reserveTicketResult) {
          case ReserveTicketResult.SuccessTicketAndEvent(var ticket, var _) -> {
            var ticketDto = TicketDto.fromDomain(ticket);
            URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(ticketDto.id())
                    .toUri();

            yield ResponseEntity.created(location).body(ticketDto);
          }
          case ReserveTicketResult.Failure(var reason) -> ResponseEntity.status(409).body(reason);
        };
      }
      case CreationResult.Failure(var errors) -> ResponseEntity.badRequest().body(errors);
    };
  }
}
