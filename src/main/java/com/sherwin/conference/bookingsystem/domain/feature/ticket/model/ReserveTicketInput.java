package com.sherwin.conference.bookingsystem.domain.feature.ticket.model;

import static com.sherwin.conference.bookingsystem.domain.feature.ticket.model.TicketFieldType.*;
import static com.sherwin.conference.bookingsystem.domain.feature.ticket.model.TicketMetaData.*;
import static com.sherwin.conference.bookingsystem.domain.feature.ticket.model.TicketMetaData.TicketFields.TALK_ID;
import static com.sherwin.conference.bookingsystem.domain.feature.ticket.model.TicketMetaData.TicketFields.USER_EMAIL;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.CommonValidations;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.FieldValue;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.Utils;
import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import java.util.List;
import java.util.Optional;

public record ReserveTicketInput(TalkId talkId, Email userEmail) {
  public static CreationResult<ReserveTicketInput> of(Long talkId, String userEmail) {
    List<Optional<FieldError>> validations = validate(talkId, userEmail);
    List<FieldError> validationErrors = Utils.collect(validations);

    return validationErrors.isEmpty() ?
        CreationResult.success(new ReserveTicketInput(new TalkId(talkId), new Email(userEmail))) :
        CreationResult.failure(validationErrors);
  }

  public static List<Optional<FieldError>> validate(Long talkId, String userEmail) {
    return List.of(
        CommonValidations.validateNullObject(
            TALK_ID.at(Operation.RESERVE_SEAT).toFieldName(), FieldValue.of(talkId)),
        CommonValidations.validateEmail(
            USER_EMAIL.at(Operation.RESERVE_SEAT).toFieldName(), FieldValue.of(userEmail)));
  }
}
