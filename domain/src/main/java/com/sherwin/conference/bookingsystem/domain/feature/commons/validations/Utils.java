package com.sherwin.conference.bookingsystem.domain.feature.commons.validations;

import java.util.List;
import java.util.Optional;

public class Utils {
  public static List<FieldError> collect(List<Optional<FieldError>> validationResults) {
    return
      validationResults.stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }
}
