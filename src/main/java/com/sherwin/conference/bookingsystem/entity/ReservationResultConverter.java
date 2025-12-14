package com.sherwin.conference.bookingsystem.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) // autoApply makes the converter default for all ReservationResult fields
public class ReservationResultConverter implements AttributeConverter<ReservationResult, String> {

  @Override
  public String convertToDatabaseColumn(ReservationResult attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.toString();
  }

  @Override
  public ReservationResult convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    // Use the static factory method defined in the sealed interface
    return ReservationResult.fromDbValue(dbData);
  }
}
