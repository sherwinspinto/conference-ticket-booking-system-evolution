package com.sherwin.conference.bookingsystem.domain.feature.talk.validations;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.CommonValidations;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.FieldName;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.FieldValue;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.Param;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonValidationsTest {
  @Test
  void testEmptyString() {
    FieldValue<String> validFirstName = FieldValue.of("SomeName");
    FieldName firstNameField = FieldName.of("talk.speaker_first_name");
    Optional<FieldError> validationResultValid = CommonValidations.validateEmptyString(firstNameField, validFirstName );
    Assertions.assertTrue(validationResultValid.isEmpty());

    FieldValue<String> invalidFirstName = FieldValue.of(" ");
    Optional<FieldError> validationResultInvalid = CommonValidations.validateEmptyString(firstNameField, invalidFirstName);
    Assertions.assertTrue(validationResultInvalid.isPresent());
    Assertions.assertInstanceOf(FieldError.Errors.EmptyStringError.class, validationResultInvalid.get());
  }

  @Test
  void testValidateOutOfRange() {
    FieldName fieldName = FieldName.of("talk.talk_time.duration");
    LocalDateTime startTime = LocalDateTime.of(2026, 1, 1, 0, 0);
    LocalDateTime endTime = startTime.plusMinutes(11);
    Duration maxDuration = Duration.ofSeconds(10*60);
    Duration minDuration = Duration.ofMinutes(1);
    Duration talkDuration = Duration.between(startTime, endTime);
    Optional<FieldError> invalidDuration = CommonValidations.validateOutOfRange(fieldName, FieldValue.of(talkDuration) , Param.of(minDuration), Param.of(maxDuration));
    Assertions.assertTrue(invalidDuration.isPresent());
    Assertions.assertInstanceOf(FieldError.Errors.OutOfRangeError.class, invalidDuration.get());
  }
}
