package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;
import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.Utils;
import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.EndTime;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.StartTime;
import com.sherwin.conference.bookingsystem.domain.feature.talk.validations.CommonValidations;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record TalkTime(StartTime startTime, EndTime endTime) {
  public static CreationResult<TalkTime> of(
      LocalDateTime startTime, LocalDateTime endTime, MetaData.PathProvider context) {
    List<FieldError> errors = Utils.collect(validate(startTime, endTime, context));
    return errors.isEmpty()
        ? CreationResult.success(new TalkTime(new StartTime(startTime), new EndTime(endTime)))
        : CreationResult.failure(errors);
  }

  public static List<Optional<FieldError>> validate(
      LocalDateTime startTime, LocalDateTime endTime, MetaData.PathProvider context) {
    var talkTimeRoot = MetaData.TalkTime.ROOT.at(context);
    return List.of(
        CommonValidations.validateNullObject(
            MetaData.TalkTime.START_TIME.at(context).toFieldName(),
            FieldError.ErrorParams.FieldValue.of(startTime)),
        CommonValidations.validateNullObject(
            MetaData.TalkTime.END_TIME.at(context).toFieldName(),
            FieldError.ErrorParams.FieldValue.of(endTime)),
        CommonValidations.validateOutOfRange(
            talkTimeRoot.toFieldName(),
            FieldError.ErrorParams.FieldValue.of(talkDuration(startTime, endTime)),
            FieldError.ErrorParams.Param.of(Duration.ofMinutes(30)),
            FieldError.ErrorParams.Param.of(Duration.ofMinutes(120))));
  }

  public static Duration talkDuration(LocalDateTime startTime, LocalDateTime endTime) {
    if (startTime == null || endTime == null) return Duration.ZERO;
    return Duration.between(startTime, endTime);
  }
}
