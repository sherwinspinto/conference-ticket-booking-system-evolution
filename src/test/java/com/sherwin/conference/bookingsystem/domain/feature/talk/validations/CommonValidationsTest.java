package com.sherwin.conference.bookingsystem.domain.feature.talk.validations;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.ValidationResult;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonValidationsTest {
  @Test
  void testEmptyString() {
    String validFirstName = "SomeName";
    ValidationResult<String> validationResultValid = CommonValidations.validateEmptyString(validFirstName, "talk.speaker_first_name");
    Assertions.assertInstanceOf(ValidationResult.Valid.class, validationResultValid);

    String invalidFirstName = " ";
    ValidationResult<String> validationResultInvalid = CommonValidations.validateEmptyString(invalidFirstName, "talk.speaker_first_name");
    Assertions.assertInstanceOf(ValidationResult.Invalid.class, validationResultInvalid);
  }

  @Test
  void testValidateOutOfRange() {
    String fieldName = "talk.talk_time.duration";
    LocalDateTime startTime = LocalDateTime.of(2026, 1, 1, 0, 0);
    LocalDateTime endTime = startTime.plusMinutes(11);
    Duration maxDuration = Duration.ofSeconds(10*60);
    Duration minDuration = Duration.ofMinutes(1);
    ValidationResult<Duration> invalidSeatCount = CommonValidations.validateOutOfRange(fieldName, Duration.between(startTime, endTime), minDuration, maxDuration);
    Assertions.assertInstanceOf(ValidationResult.Invalid.class, invalidSeatCount);
  }
}
