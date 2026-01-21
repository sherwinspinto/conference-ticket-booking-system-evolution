package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.Errors;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.Errors.NullObjectError;
import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.FirstName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.Errors.*;
import static com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.*;
import static org.junit.jupiter.api.Assertions.*;

class AddTalkTest {

  @Test
  @DisplayName("Should create AddTalk successfully when all data is valid")
  void testAddTalkSuccess() {
    FirstName firstName = new FirstName("John");
    LastName lastName = new LastName("Doe");
    Speaker speaker = new Speaker(firstName, lastName);
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.plusMinutes(60);
    TalkTime talkTime = new TalkTime(new StartTime(startTime), new EndTime(endTime));

    CreationResult<AddTalk> result = AddTalk.of(speaker, "Clean Architecture", talkTime, 50);

    assertInstanceOf(CreationResult.Success.class, result);
  }

  @Test
  @DisplayName("Should report multiple errors when speaker names are empty")
  void testAddTalkSpeakerFailure() {
    // Empty names should trigger Speaker validation
    FirstName firstName = new FirstName(" ");
    LastName lastName = new LastName("");
    Speaker speaker = new Speaker(firstName, lastName);
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.plusMinutes(45);
    TalkTime talkTime = new TalkTime(new StartTime(startTime), new EndTime(endTime));

    CreationResult<AddTalk> result = AddTalk.of(speaker, "Java Patterns", talkTime, 50);

    assertFailurePath(
        result, TalkMetaData.Speaker.FIRST_NAME.at(TalkMetaData.Operation.ADD).getPath());
    assertFailurePath(
        result, TalkMetaData.Speaker.LAST_NAME.at(TalkMetaData.Operation.ADD).getPath());
  }

  @Test
  @DisplayName("Should report error when talk duration is too short")
  void testAddTalkDurationFailure() {
    FirstName firstName = new FirstName("John");
    LastName lastName = new LastName("Doe");
    Speaker speaker = new Speaker(firstName, lastName);
    // Only 10 minutes - should fail (min 30 mins)
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.plusMinutes(10);
    TalkTime talkTime = new TalkTime(new StartTime(startTime), new EndTime(endTime));

    CreationResult<AddTalk> result = AddTalk.of(speaker, "Quick Talk", talkTime, 50);

    // This checks the TalkTime root path we fixed earlier
    assertFailurePath(result, TalkMetaData.TalkTime.ROOT.at(TalkMetaData.Operation.ADD).getPath());
  }

  @Test
  @DisplayName("Should report error when seat count is out of range")
  void testAddTalkSeatCountFailure() {
    FirstName firstName = new FirstName("John");
    LastName lastName = new LastName("Doe");
    Speaker speaker = new Speaker(firstName, lastName);
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.plusMinutes(60);
    TalkTime talkTime = new TalkTime(new StartTime(startTime), new EndTime(endTime));

    CreationResult<AddTalk> result = AddTalk.of(speaker, "Big Talk", talkTime, 500); // Max 100

    assertFailurePath(
        result, TalkMetaData.TalkFields.SEAT_COUNT.at(TalkMetaData.Operation.ADD).getPath());
  }

  /** Helper to verify that a specific field path exists in the failure results. */
  private void assertFailurePath(CreationResult<?> result, String expectedPath) {
    if (result instanceof CreationResult.Failure(List<FieldError> errors)) {
      boolean pathFound =
          errors.stream()
              .filter(e -> e instanceof Errors)
              .map(e -> (Errors) e)
              .anyMatch(e -> getFieldName(e).equals(expectedPath));

      assertTrue(
          pathFound, "Expected error path [" + expectedPath + "] was not found in: " + errors);
    } else {
      fail("Expected a Failure result, but got Success");
    }
  }

  private String getFieldName(Errors error) {
    return switch (error) {
      case NullObjectError e -> e.fieldName().name();
      case EmptyStringError e -> e.fieldName().name();
      case OutOfRangeError<?> e -> e.fieldName().name();
      case InvalidEmailError e -> e.fieldName().name();
    };
  }
}
