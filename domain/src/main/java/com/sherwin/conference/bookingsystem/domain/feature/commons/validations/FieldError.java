package com.sherwin.conference.bookingsystem.domain.feature.commons.validations;

import static com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.*;

public sealed interface FieldError {
  sealed interface Errors extends FieldError {

    record NullObjectError(FieldName fieldName) implements Errors {}

    record EmptyStringError(FieldName fieldName) implements Errors {}

    record OutOfRangeError<T>(
        FieldName fieldName,
        ErrorParams.FieldValue<T> fieldValue,
        ErrorParams.Param<T> minParam,
        ErrorParams.Param<T> maxParam)
        implements Errors {}

    record InvalidEmailError(FieldName fieldName) implements Errors {}
  }

  sealed interface ErrorParams extends FieldError {
    record FieldName(String name) implements ErrorParams {
      public static FieldName of(String fieldName) {
        return new FieldName(fieldName);
      }
    }

    record FieldValue<T>(T value) implements ErrorParams {
      public static <T> FieldValue<T> of(T input) {
        return new FieldValue<>(input);
      }
    }

    record Param<T>(T value) implements ErrorParams {
      public static <T> Param<T> of(T input) {
        return new Param<>(input);
      }
    }
  }
}
