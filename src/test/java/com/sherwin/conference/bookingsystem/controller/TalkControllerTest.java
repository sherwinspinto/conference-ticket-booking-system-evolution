package com.sherwin.conference.bookingsystem.controller;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TalkControllerTest {
  public static final String BASE_URL = "http://localhost:%d/api";
  private final TestRestTemplate testRestTemplate;
  @LocalServerPort int localServerPort;

  @Autowired
  public TalkControllerTest(TestRestTemplate testRestTemplate) {
    this.testRestTemplate = testRestTemplate;
  }

  @Test
  void testCreateTalk() {
    System.out.println(
        testRestTemplate.postForEntity(
            URI.create(createCreateTalkURL(localServerPort, "talk1", 100)), null, String.class));
  }

  public static String createBaseURL(int port) {
    return String.format(BASE_URL, port);
  }

  public static String createCreateTalkURL(int port, String talkName, int totalSeats) {
    return createBaseURL(port)
        + "/"
        + "talks/create?name="
        + talkName
        + "&totalSeats="
        + totalSeats;
  }
}
