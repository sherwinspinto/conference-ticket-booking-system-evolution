package com.sherwin.conference.bookingsystem.domain.feature.commons.validations;

import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;

import java.util.List;

public sealed interface ValidationResult<T> {
  record Valid<T>(T value) implements ValidationResult<T> {}

  record Invalid<T>(FieldError fieldError) implements ValidationResult<T> {}

  static <T> Valid<T> validField(T value) {
    return new Valid<>(value);
  }

  static <T> Invalid<T> invalidField(FieldError fieldError) {
    return new Invalid<>(fieldError);
  }

  static List<FieldError> collect(List<ValidationResult<?>> validationResults) {
      return validationResults.stream()
        .filter(validationResult -> validationResult instanceof ValidationResult.Invalid)
        .map(validationResult -> ((ValidationResult.Invalid<?>) validationResult).fieldError())
        .toList();
  }
}
