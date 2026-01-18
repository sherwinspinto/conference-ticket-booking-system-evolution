package com.sherwin.conference.bookingsystem.infrastructure.db.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;

@Converter
public class TalkTimeConverter implements AttributeConverter<TalkTimeJson, String> {
  private final ObjectMapper objectMapper;

  @Autowired
  public TalkTimeConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String convertToDatabaseColumn(TalkTimeJson talkTimeJson) {
    try {
      return talkTimeJson == null ? null : objectMapper.writeValueAsString(talkTimeJson);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public TalkTimeJson convertToEntityAttribute(String s) {
    try {
      return s == null ? null : objectMapper.readValue(s, TalkTimeJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
