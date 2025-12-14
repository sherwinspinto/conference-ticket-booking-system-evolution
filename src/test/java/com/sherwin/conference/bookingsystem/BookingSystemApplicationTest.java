package com.sherwin.conference.bookingsystem;

import com.sherwin.conference.bookingsystem.controller.TalkController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookingSystemApplicationTest {

  private final TalkController talkController;

  @Autowired
  public BookingSystemApplicationTest(TalkController talkController) {
    this.talkController = talkController;
  }

  @Test
  void contextLoads() {
    Assertions.assertNotNull(talkController);
  }
}
