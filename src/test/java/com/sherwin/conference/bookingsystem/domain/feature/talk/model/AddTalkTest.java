package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import static org.junit.jupiter.api.Assertions.*;

import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddTalkTest {
  @Test
  void testAddTalkCreation() {
    String speakerFirstName = "speakerLastName";
    String speakerLastName = "speakerLastName";
    String talkName = "talkName";
    LocalDateTime startTime = LocalDateTime.of(2026, 1, 1, 0, 0);
    LocalDateTime endTime = startTime.plusMinutes(60);
    TalkTime talkTime = new TalkTime(startTime, endTime);
    int seatCount = 1000;

    CreationResult<AddTalk> addTalkCreationResult =
        AddTalk.of(speakerFirstName, speakerLastName, talkName, talkTime, seatCount);

    Assertions.assertInstanceOf(CreationResult.Success.class, addTalkCreationResult);
  }
}
