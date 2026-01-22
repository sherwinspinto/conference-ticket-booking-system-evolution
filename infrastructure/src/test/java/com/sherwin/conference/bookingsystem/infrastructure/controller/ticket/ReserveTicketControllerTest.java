package com.sherwin.conference.bookingsystem.infrastructure.controller.ticket;

import static org.junit.jupiter.api.Assertions.*;

import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TicketRepository;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.AddTalkDto;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.SpeakerDto;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.TalkDto;
import com.sherwin.conference.bookingsystem.infrastructure.dto.talk.TalkTimeDto;
import com.sherwin.conference.bookingsystem.infrastructure.dto.ticket.ReserveTicketDto;
import com.sherwin.conference.bookingsystem.infrastructure.dto.ticket.TicketDto;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReserveTicketControllerTest {
  private final TicketRepository ticketRepository;
  private final TestRestTemplate testRestTemplate;

  @LocalServerPort private int localServerPort;

  @Autowired
  public ReserveTicketControllerTest(
      TicketRepository ticketRepository, TestRestTemplate testRestTemplate) {
    this.ticketRepository = ticketRepository;
    this.testRestTemplate = testRestTemplate;
  }

  @Test
  void test200TicketsIntegration() throws Exception {
    String talkName = "talk1";

    ResponseEntity<TalkDto> talkResponseEntity =
        testRestTemplate.postForEntity(
            URI.create(PathCreator.createAddTalkUrl(localServerPort)),
            //        URI.create("http://localhost:"+localServerPort+"/api/talks"),
            createAddTalkPayload(
                talkName,
                100,
                "John",
                "Doe",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)),
            TalkDto.class);
    assertEquals(HttpStatus.CREATED, talkResponseEntity.getStatusCode());

    ExecutorService executorService = Executors.newFixedThreadPool(100);

    for (int i = 1; i <= 400; i++) {
      executorService.submit(
          () ->
              testRestTemplate.postForEntity(
                  URI.create(
                      PathCreator.createReserveTicketUrl(
                          localServerPort, talkResponseEntity.getBody().id())),
                  createReserveTicketPayload(
                      "testemail@tester.com", talkResponseEntity.getBody().id()),
                  TicketDto.class));
    }
    executorService.shutdown();
    executorService.awaitTermination(10, TimeUnit.SECONDS);
    assertEquals(100, ticketRepository.count());
  }

  interface PathCreator {
    String getPath();

    enum Domain implements PathCreator {
      TALKS("/talks"),
      TICKETS("/tickets");
      private final String path;

      Domain(String path) {
        this.path = path;
      }

      public String getPath() {
        return path;
      }
    }

    interface Commands extends PathCreator {
      enum TalkCommands implements Commands {
        ADD_TALK(Domain.TALKS, "");
        private final String path;
        private final Domain domain;

        TalkCommands(Domain domain, String path) {
          this.domain = domain;
          this.path = path;
        }

        public String getPath() {
          return domain.getPath() + path;
        }
      }

      enum TicketCommands implements Commands {
        RESERVE_TICKET(Domain.TICKETS, "/talks/{talkId}/reserve");
        private final String path;
        private final Domain domain;

        TicketCommands(Domain domain, String path) {
          this.domain = domain;
          this.path = path;
        }

        @Override
        public String getPath() {
          return domain.getPath() + path;
        }
      }
    }

    static String createBaseURL(int port) {
      return "http://localhost:" + port + "/api";
    }

    static String createAddTalkUrl(int port) {
      return createBaseURL(port) + Commands.TalkCommands.ADD_TALK.getPath();
    }

    static String createReserveTicketUrl(int port, Long talkId) {
      return createBaseURL(port)
          + Commands.TicketCommands.RESERVE_TICKET.getPath().replace("{talkId}", talkId.toString());
    }
  }

  static ReserveTicketDto createReserveTicketPayload(String userEmail, long talkId) {
    return new ReserveTicketDto(talkId, userEmail);
  }

  static AddTalkDto createAddTalkPayload(
      String talkName,
      int seatCount,
      String speakerFirstName,
      String speakerLastName,
      LocalDateTime startTime,
      LocalDateTime endTime) {
    return new AddTalkDto(
        talkName,
        new SpeakerDto(speakerFirstName, speakerLastName),
        new TalkTimeDto(startTime, endTime),
        seatCount);
  }
}
