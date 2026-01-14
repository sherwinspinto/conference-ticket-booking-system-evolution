package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.ValidationResult;
import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.talk.validations.CommonValidations;
import java.util.ArrayList;
import java.util.List;

public record AddTalk(
    String speakerFirstName,
    String speakerLastName,
    String talkName,
    TalkTime talkTime,
    int seatCount) implements TalkModel {

  public static CreationResult<AddTalk> of(
      String speakerFirstName,
      String speakerLastName,
      String talkName,
      TalkTime talkTime,
      int seatCount) {
    List<FieldError> fieldErrors =
        ValidationResult.collect(
            validate(speakerFirstName, speakerLastName, talkName, talkTime, seatCount));
    if (fieldErrors.isEmpty())
      return CreationResult.success(
          new AddTalk(speakerFirstName, speakerLastName, talkName, talkTime, seatCount));
    return CreationResult.failure(fieldErrors);
  }

  public static List<ValidationResult<?>> validate(
      String speakerFirstName,
      String speakerLastName,
      String talkName,
      TalkTime talkTime,
      int seatCount) {
    List<ValidationResult<?>> validations = new ArrayList<>();
    validations.add(
        CommonValidations.validateEmptyString("talk.speaker_first_name", speakerFirstName));
    validations.add(
        CommonValidations.validateEmptyString("talk.speaker_last_name", speakerLastName));
    validations.add(CommonValidations.validateEmptyString("talk.talk_name", talkName));
    validations.add(CommonValidations.validateNullObject("talk.talk_time", talkTime));
    validations.add(CommonValidations.validateOutOfRange("talk.seat_count", seatCount, 0, 100));
    if (talkTime != null) {
      validations.add(
          CommonValidations.validateNullObject("talk.talk_time.start_time", talkTime.startTime()));
      validations.add(
          CommonValidations.validateNullObject("talk.talk_time.start_time", talkTime.startTime()));
    }
    return List.copyOf(validations);
  }
}
