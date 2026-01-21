package com.sherwin.conference.bookingsystem.domain.feature.commons.validations;

import static com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.Errors.*;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.FieldName;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.FieldValue;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.Param;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.Errors.EmptyStringError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.Errors.NullObjectError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.Errors.OutOfRangeError;
import java.util.Optional;
import java.util.regex.Pattern;

public class CommonValidations {
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

  public static boolean isNotBlank(String test) {
    return test != null && !test.trim().isEmpty();
  }

  public static boolean isBlank(String test) {
    return !isNotBlank(test);
  }

  public static <T extends Comparable<T>> Optional<FieldError> validateOutOfRange(
      FieldName fieldName, FieldValue<T> fieldValue, Param<T> min, Param<T> max) {
    if (fieldValue.value().compareTo(min.value()) < 0
        || fieldValue.value().compareTo(max.value()) > 0)
      return Optional.of(new OutOfRangeError<>(fieldName, fieldValue, min, max));
    return Optional.empty();
  }

  public static Optional<FieldError> validateEmptyString(
      FieldName fieldName, FieldValue<String> fieldValue) {
    if (isBlank(fieldValue.value())) return Optional.of(new EmptyStringError(fieldName));
    return Optional.empty();
  }

  public static <T> Optional<FieldError> validateNullObject(
      FieldName fieldName, FieldValue<T> fieldValue) {
    if (fieldValue.value() == null) return Optional.of(new NullObjectError(fieldName));
    return Optional.empty();
  }

  public static Optional<FieldError> validateEmail(
      FieldName fieldName, FieldValue<String> fieldValue) {
    return isValidEmail(fieldValue.value())
        ? Optional.empty()
        : Optional.of(new InvalidEmailError(fieldName));
  }

  public static boolean isValidEmail(String email) {
    return isBlank(email) || EMAIL_PATTERN.matcher(email).matches();
  }
}
