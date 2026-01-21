package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import static com.sherwin.conference.bookingsystem.domain.feature.commons.validations.Utils.collect;
import static com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.*;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.FieldValue;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams.Param;
import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.model.Version;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.CommonValidations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record UpdateTalk(TalkId id, TalkName talkName, Speaker speaker, TalkTime talkTime, SeatCount seatCount, Version version) {

  public static CreationResult<UpdateTalk> of(Long id, String talkName, Speaker speaker, TalkTime talkTime, int seatCount, Long version) {
    List<FieldError> errors = collect(validate(id, talkName, speaker, talkTime, seatCount, version));
    return errors.isEmpty()
      ? CreationResult.success(new UpdateTalk(new TalkId(id), new TalkName(talkName), speaker, talkTime, new SeatCount(seatCount), new Version(version)))
      : CreationResult.failure(errors);
  }

  public static List<Optional<FieldError>> validate(Long id, String talkName, Speaker speaker, TalkTime talkTime, int seatCount, Long version) {
    List<Optional<FieldError>> validations = new ArrayList<>();
    var op = TalkMetaData.Operation.UPDATE;

    // 1. Technical/Identity Validations
    validations.add(CommonValidations.validateNullObject(TalkMetaData.TalkFields.ID.at(op).toFieldName(), FieldValue.of(id)));
    validations.add(CommonValidations.validateNullObject(TalkMetaData.TalkFields.VERSION.at(op).toFieldName(), FieldValue.of(version)));

    // 2. Simple Field Validations
    validations.add(CommonValidations.validateEmptyString(TalkMetaData.TalkFields.NAME.at(op).toFieldName(), FieldValue.of(talkName)));
    validations.add(CommonValidations.validateOutOfRange(TalkMetaData.TalkFields.SEAT_COUNT.at(op).toFieldName(), FieldValue.of(seatCount), Param.of(0), Param.of(100)));

    // 3. Nested Object Validations (Delegated to sub-models)
    validations.add(CommonValidations.validateNullObject(TalkMetaData.TalkFields.SPEAKER.at(op).toFieldName(), FieldValue.of(speaker)));
    if (speaker != null) {
      validations.addAll(Speaker.validate(speaker.firstName().value(), speaker.lastName().value(), TalkMetaData.Speaker.ROOT.at(op)));
    }

    validations.add(CommonValidations.validateNullObject(TalkMetaData.TalkFields.TALK_TIME.at(op).toFieldName(), FieldValue.of(talkTime)));
    if (talkTime != null) {
      validations.addAll(TalkTime.validate(talkTime.startTime().value(), talkTime.endTime().value(), TalkMetaData.TalkTime.ROOT.at(op)));
    }

    return List.copyOf(validations);
  }
}
