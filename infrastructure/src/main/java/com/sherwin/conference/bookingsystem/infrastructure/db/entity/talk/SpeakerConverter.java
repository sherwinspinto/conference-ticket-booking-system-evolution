package com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;

@Converter
public class SpeakerConverter implements AttributeConverter<SpeakerJson, String> {
  private final ObjectMapper objectMapper;

  @Autowired
  public SpeakerConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String convertToDatabaseColumn(SpeakerJson speakerJson) {
    try {
      return speakerJson == null ? null : objectMapper.writeValueAsString(speakerJson);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public SpeakerJson convertToEntityAttribute(String json) {
    try {
      return json == null || json.isBlank() ? null : objectMapper.readValue(json, SpeakerJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
