package com.sherwin.conference.bookingsystem.domain.feature.commons.validations;

import java.time.temporal.ChronoUnit;

public sealed interface FieldError {
  record EmptyString(String fieldName) implements FieldError {}
  record NullObject(String fieldName) implements FieldError {}
  record IntegerOutOfRange(String fieldName, int min, int max) implements FieldError {}
  record DurationOutOfRange<T>(String fieldName, T actual, T expected, String unit) implements FieldError {}
  record InvalidParameter2<T>(String fieldName1, String fieldName2, T field1Value, T Field2Value ) implements FieldError {}
  record GeneralInvalid(String fieldName) implements FieldError {}
}
