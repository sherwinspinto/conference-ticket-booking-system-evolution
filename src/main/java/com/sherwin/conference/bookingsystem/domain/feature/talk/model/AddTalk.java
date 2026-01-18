package com.sherwin.conference.bookingsystem.domain.feature.talk.model;



import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.FieldValue;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.Param;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.Utils;
import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.talk.validations.CommonValidations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.*;

public record AddTalk(Speaker speaker, TalkName talkName, TalkTime talkTime, SeatCount seatCount)
    implements TalkModel {

  public static CreationResult<AddTalk> of(
      Speaker speaker, String talkName, TalkTime talkTime, int seatCount) {
    List<FieldError> fieldErrors = Utils.collect(validate(speaker, talkName, talkTime, seatCount));
    return fieldErrors.isEmpty()
        ? CreationResult.success(
            new AddTalk(speaker, new TalkName(talkName), talkTime, new SeatCount(seatCount)))
        : CreationResult.failure(fieldErrors);
  }

  public static List<Optional<FieldError>> validate(
      Speaker speaker, String talkName, TalkTime talkTime, int seatCount) {

    var op = MetaData.Operation.ADD;
    List<Optional<FieldError>> validations = new ArrayList<>();
    validations.add(
        CommonValidations.validateEmptyString(
            MetaData.TalkFields.NAME.at(op).toFieldName(), FieldValue.of(talkName)));
    validations.add(
        CommonValidations.validateOutOfRange(
            MetaData.TalkFields.SEAT_COUNT.at(op).toFieldName(),
            FieldValue.of(seatCount),
            Param.of(0),
            Param.of(100)));

    validations.add(
        CommonValidations.validateNullObject(
            MetaData.TalkFields.TALK_TIME.at(op).toFieldName(), FieldValue.of(talkTime)));
    if (talkTime != null)
      validations.addAll(TalkTime.validate(talkTime.startTime().value(), talkTime.endTime().value(), op));

    validations.add(
        CommonValidations.validateNullObject(
            MetaData.TalkFields.SPEAKER.at(op).toFieldName(), FieldValue.of(speaker)));
    if (speaker != null)
      validations.addAll(Speaker.validate(speaker.firstName().value(), speaker.lastName().value(), op));

    return List.copyOf(validations);
  }
}
