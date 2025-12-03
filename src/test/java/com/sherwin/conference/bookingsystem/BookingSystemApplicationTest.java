package com.sherwin.conference.bookingsystem;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingSystemApplicationTest {
  @Autowired
  TestRestTemplate testRestTemplate;

  // In a @SpringBootTest class
  @Test
  void concurrentReserve() throws InterruptedException {
    int threads = 50;
    ExecutorService executor = Executors.newFixedThreadPool(threads);
    List<CompletableFuture<String>> futures = IntStream.range(0, threads)
      .mapToObj(i -> CompletableFuture.supplyAsync(() -> testRestTemplate.postForObject(
        "http://localhost:8080/api/tickets/reserve?talkId=1&userEmail=user" + i + "@ex.com", null, String.class)))
      .toList();
    futures.forEach(CompletableFuture::join);
    // Check DB: Should have >100 if races win
  }
}
