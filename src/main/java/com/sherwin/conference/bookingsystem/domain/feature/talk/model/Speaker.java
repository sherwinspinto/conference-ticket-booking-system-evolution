package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import static com.sherwin.conference.bookingsystem.domain.feature.commons.validations.Utils.collect;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.FieldValue;
import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.FirstName;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.LastName;
import com.sherwin.conference.bookingsystem.domain.feature.talk.validations.CommonValidations;
import java.util.List;
import java.util.Optional;

public record Speaker(FirstName firstName, LastName lastName) {
  public static CreationResult<Speaker> of(
      String firstName, String lastName, MetaData.PathProvider context) {
    List<FieldError> errors = collect(validate(firstName, lastName, context));
    return errors.isEmpty()
        ? CreationResult.success(new Speaker(new FirstName(firstName), new LastName(lastName)))
        : CreationResult.failure(errors);
  }

  public static List<Optional<FieldError>> validate(
      String firstName, String lastName, MetaData.PathProvider context) {
    return List.of(
        CommonValidations.validateEmptyString(
            MetaData.Speaker.FIRST_NAME.at(context).toFieldName(), FieldValue.of(firstName)),
        CommonValidations.validateEmptyString(
            MetaData.Speaker.LAST_NAME.at(context).toFieldName(), FieldValue.of(lastName)));
  }
}
