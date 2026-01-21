package com.sherwin.conference.bookingsystem.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketControllerTest {
  //  private final TicketRepository ticketRepository;
  //  private final TestRestTemplate testRestTemplate;
  //  private final TalkRepository talkRepository;
  //
  //  @LocalServerPort private int localServerPort;
  //
  //  @Autowired
  //  public TicketControllerTest(TicketRepository ticketRepository, TestRestTemplate
  // testRestTemplate, TalkRepository talkRepository) {
  //    this.ticketRepository = ticketRepository;
  //    this.testRestTemplate = testRestTemplate;
  //    this.talkRepository = talkRepository;
  //  }
  //
  //  @Test
  //  void test100TicketsIntegration() throws Exception {
  //    ResponseEntity<String> talkResponseEntity =
  //        testRestTemplate.postForEntity(
  //            URI.create(CreateTalkControllerTest.createCreateTalkURL(localServerPort, "talk1",
  // 100)),
  //            null,
  //            String.class);
  //    assertEquals(HttpStatus.OK, talkResponseEntity.getStatusCode());
  //    ExecutorService executorService = Executors.newFixedThreadPool(100);
  //    for (int i = 1; i <= 120; i++) {
  //      executorService.submit(
  //          () ->
  //              testRestTemplate.postForEntity(
  //                  URI.create(
  //                      createReserveTicketAPIURL(localServerPort, "1", "testemail@tester.com")),
  //                  null,
  //                  String.class));
  //    }
  //    executorService.awaitTermination(10, TimeUnit.SECONDS);
  //    TicketEntity ticketEntity = new TicketEntity();
  //    ticketEntity.setTalkId(1L);
  //    assertEquals(100, ticketRepository.count(Example.of(ticketEntity)));
  //  }
  //
  //  @Test
  //  void test200TicketsIntegration() throws Exception {
  //    String talkName = "talk2";
  //    ResponseEntity<String> talkResponseEntity =
  //      testRestTemplate.postForEntity(
  //        URI.create(CreateTalkControllerTest.createCreateTalkURL(localServerPort, talkName,
  // 100)),
  //        null,
  //        String.class);
  //    assertEquals(HttpStatus.OK, talkResponseEntity.getStatusCode());
  //    ExecutorService executorService = Executors.newFixedThreadPool(100);
  //    TalkEntity talkEntity = TalkEntity.createWithNameAndTotalSeats(talkName, 100);
  //    Long talk2Id = talkRepository.findOne(Example.of(talkEntity))
  //      .orElseThrow().getId();
  //
  //    for (int i = 1; i <= 200; i++) {
  //      executorService.submit(
  //        () ->
  //          testRestTemplate.postForEntity(
  //            URI.create(
  //              createReserveTicketAPIURL(localServerPort, talk2Id+"", "testemail@tester.com")),
  //            null,
  //            String.class));
  //    }
  //    executorService.awaitTermination(10, TimeUnit.SECONDS);
  //    TicketEntity ticketEntity = new TicketEntity();
  //    ticketEntity.setTalkId(talk2Id);
  //    assertEquals(100, ticketRepository.count(Example.of(ticketEntity)));
  //  }
  //
  //  public static String createTicketAPIURL(int port) {
  //    return new StringBuilder()
  //        .append(CreateTalkControllerTest.createBaseURL(port))
  //        .append("/")
  //        .append("tickets")
  //        .toString();
  //  }
  //
  //  public static String createReserveTicketAPIURL(int port, String talkId, String userEmail) {
  //    return new StringBuilder()
  //      .append(createTicketAPIURL(port))
  //        .append("/reserve")
  //        .append("?talkId=")
  //        .append(talkId)
  //        .append("&userEmail=")
  //        .append(userEmail)
  //        .toString();
  //  }
}
