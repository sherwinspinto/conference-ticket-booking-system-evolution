package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddTalkTest {

  @Test
  @DisplayName("Should create AddTalk successfully when all data is valid")
  void testAddTalkSuccess() {
    Speaker speaker = new Speaker("John", "Doe");
    TalkTime talkTime = new TalkTime(LocalDateTime.now(), LocalDateTime.now().plusMinutes(60));

    CreationResult<AddTalk> result = AddTalk.of(speaker, "Clean Architecture", talkTime, 50);

    assertInstanceOf(CreationResult.Success.class, result);
  }

  @Test
  @DisplayName("Should report multiple errors when speaker names are empty")
  void testAddTalkSpeakerFailure() {
    // Empty names should trigger Speaker validation
    Speaker speaker = new Speaker(" ", "");
    TalkTime talkTime = new TalkTime(LocalDateTime.now(), LocalDateTime.now().plusMinutes(45));

    CreationResult<AddTalk> result = AddTalk.of(speaker, "Java Patterns", talkTime, 50);

    assertFailurePath(result, MetaData.Speaker.FIRST_NAME.at(MetaData.Operation.ADD).getPath());
    assertFailurePath(result, MetaData.Speaker.LAST_NAME.at(MetaData.Operation.ADD).getPath());
  }

  @Test
  @DisplayName("Should report error when talk duration is too short")
  void testAddTalkDurationFailure() {
    Speaker speaker = new Speaker("John", "Doe");
    // Only 10 minutes - should fail (min 30 mins)
    TalkTime talkTime = new TalkTime(LocalDateTime.now(), LocalDateTime.now().plusMinutes(10));

    CreationResult<AddTalk> result = AddTalk.of(speaker, "Quick Talk", talkTime, 50);

    // This checks the TalkTime root path we fixed earlier
    assertFailurePath(result, MetaData.TalkTime.ROOT.at(MetaData.Operation.ADD).getPath());
  }

  @Test
  @DisplayName("Should report error when seat count is out of range")
  void testAddTalkSeatCountFailure() {
    Speaker speaker = new Speaker("John", "Doe");
    TalkTime talkTime = new TalkTime(LocalDateTime.now(), LocalDateTime.now().plusMinutes(60));

    CreationResult<AddTalk> result = AddTalk.of(speaker, "Big Talk", talkTime, 500); // Max 100

    assertFailurePath(result, MetaData.TalkFields.SEAT_COUNT.at(MetaData.Operation.ADD).getPath());
  }

  /**
   * Helper to verify that a specific field path exists in the failure results.
   */
  private void assertFailurePath(CreationResult<?> result, String expectedPath) {
    if (result instanceof CreationResult.Failure(List<FieldError> errors)) {
      boolean pathFound = errors.stream()
        .filter(e -> e instanceof FieldError.Errors)
        .map(e -> (FieldError.Errors) e)
        .anyMatch(e -> getFieldName(e).equals(expectedPath));

      assertTrue(pathFound, "Expected error path [" + expectedPath + "] was not found in: " + errors);
    } else {
      fail("Expected a Failure result, but got Success");
    }
  }

  private String getFieldName(FieldError.Errors error) {
    return switch (error) {
      case FieldError.Errors.NullObjectError e -> e.fieldName().name();
      case FieldError.Errors.EmptyStringError e -> e.fieldName().name();
      case FieldError.Errors.OutOfRangeError<?> e -> e.fieldName().name();
    };
  }
}
