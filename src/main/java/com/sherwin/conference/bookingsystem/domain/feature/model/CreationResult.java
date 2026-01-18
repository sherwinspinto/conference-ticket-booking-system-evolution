package com.sherwin.conference.bookingsystem.domain.feature.model;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import java.util.List;

public sealed interface CreationResult<T> {
  record Success<T>(T value) implements CreationResult<T> {}

  record Failure<T>(List<FieldError> validationErrors) implements CreationResult<T> {}

  static <T> Success<T> success(T value) {
    return new Success<>(value);
  }

  static <T> CreationResult<T> failure(List<FieldError> validationErrors) {
    return new Failure<>(List.copyOf(validationErrors));
  }
}
