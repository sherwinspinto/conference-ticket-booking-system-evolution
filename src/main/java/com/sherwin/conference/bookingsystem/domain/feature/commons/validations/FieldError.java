package com.sherwin.conference.bookingsystem.domain.feature.commons.validations;

public sealed interface FieldError {
  sealed interface Errors extends FieldError {

    record NullObjectError(ErrorParams.FieldName fieldName) implements Errors {}

    record EmptyStringError(ErrorParams.FieldName fieldName) implements Errors {}

    record OutOfRangeError<T>(
        ErrorParams.FieldName fieldName,
        ErrorParams.FieldValue<T> fieldValue,
        ErrorParams.Param<T> minParam,
        ErrorParams.Param<T> maxParam)
        implements Errors {}
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
