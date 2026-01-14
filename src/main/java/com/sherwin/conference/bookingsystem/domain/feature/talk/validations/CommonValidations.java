package com.sherwin.conference.bookingsystem.domain.feature.talk.validations;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.ValidationResult;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkTime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CommonValidations {
  public static boolean isNotBlank(String test) {
    return test != null && !test.trim().isEmpty();
  }

  public static boolean isBlank(String test) {
    return !isNotBlank(test);
  }

  public static <T extends Comparable<T>> ValidationResult<T> validateOutOfRange(String fieldName, T input, T min, T max) {
    if (input.compareTo(min) < 0  ||  input.compareTo(max) > 0 )
      return ValidationResult.invalidField(new FieldError.IntegerOutOfRange("talk.seat_count", 0, 200));
    return ValidationResult.validField(input);
  }

  public static ValidationResult<String> validateEmptyString(String fieldName, String input) {
    if (isBlank(input))
      return ValidationResult.invalidField(new FieldError.EmptyString(fieldName));
    return ValidationResult.validField(input);
  }

  public static <T> ValidationResult<T> validateNullObject(String fieldName, T input) {
    if (input == null)
      ValidationResult.invalidField(new FieldError.NullObject(fieldName));
    return ValidationResult.validField(input);
  }

  public static ValidationResult<TalkTime> validateTalkTimeDuration(
      TalkTime talkTime, Duration maxDurationInMin) {
    LocalDateTime talkStartTime = talkTime.startTime();
    LocalDateTime talkEndTime = talkTime.endTime();
    Duration talkDuration = Duration.between(talkStartTime, talkEndTime);
    return switch (talkDuration) {
      case Duration duration when duration.isNegative() ->
          ValidationResult.invalidField(
              new FieldError.InvalidParameter2<LocalDateTime>(
                  "talk.talk_time.start_time",
                  "talk.talk_time.end_time",
                  talkStartTime,
                  talkEndTime));
      case Duration duration when duration.compareTo(maxDurationInMin) > 0 ->
          ValidationResult.invalidField(
              new FieldError.DurationOutOfRange<Long>(
                  "talk.talk_time",
                  duration.toMinutes(),
                  maxDurationInMin.toMinutes(),
                  ChronoUnit.MINUTES.name()));

      default -> ValidationResult.validField(talkTime);
    };
  }
}
