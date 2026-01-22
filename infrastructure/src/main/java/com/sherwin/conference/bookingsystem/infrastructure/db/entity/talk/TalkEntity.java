package com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk;

import com.sherwin.conference.bookingsystem.domain.feature.model.CreationResult;
import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TalkId;
import com.sherwin.conference.bookingsystem.domain.feature.model.Version;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.*;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.EndTime;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.FirstName;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.LastName;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.TalkFieldType.StartTime;
import jakarta.persistence.*;

@Entity
@Table(name = "talks")
public class TalkEntity {

  public TalkEntity() {
    this.name = null;
    this.totalSeats = 0;
  }

  private TalkEntity(String name, int totalSeats) {
    this.name = name;
    this.totalSeats = totalSeats;
  }

  @jakarta.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private final String name;

  @Convert(converter = SpeakerConverter.class)
  @Column(name = "speaker_json", columnDefinition = "TEXT")
  private SpeakerJson speakerJson;

  @Convert(converter = TalkTimeConverter.class)
  @Column(name = "talk_time_json", columnDefinition = "TEXT")
  private TalkTimeJson talkTimeJson;

  private final int totalSeats;

  private int reservedSeats = 0;

  @jakarta.persistence.Version private Long version;

  public TalkEntity(
      Long id,
      String name,
      SpeakerJson speakerJson,
      TalkTimeJson talkTimeJson,
      int totalSeats,
      int reservedSeats,
      Long version) {
    this.id = id;
    this.name = name;
    this.speakerJson = speakerJson;
    this.talkTimeJson = talkTimeJson;
    this.totalSeats = totalSeats;
    this.reservedSeats = reservedSeats;
    this.version = version;
  }

  public TalkEntity(Long id, String name, int totalSeats, int reservedSeats, Long version) {
    this.id = id;
    this.name = name;
    this.totalSeats = totalSeats;
    this.reservedSeats = reservedSeats;
    this.version = version;
  }

  /** Used for AddTalk operation to construct TalkEntity from AddTalk object */
  public TalkEntity(
      String talkName, SpeakerJson speakerJson, TalkTimeJson talkTimeJson, int totalSeats) {
    this.name = talkName;
    this.speakerJson = speakerJson;
    this.talkTimeJson = talkTimeJson;
    this.totalSeats = totalSeats;
  }

  /**
   * Used for UpdateTalk operation to construct TalkEntity from UpdateTalk object The reservedSeats
   * is left out since that should happen as a separate command/operation
   *
   * @param talkName
   * @param speakerJson
   * @param talkTimeJson
   * @param totalSeats
   */
  public TalkEntity(
      Long id,
      String talkName,
      SpeakerJson speakerJson,
      TalkTimeJson talkTimeJson,
      int totalSeats,
      Long version) {
    this.id = id;
    this.name = talkName;
    this.speakerJson = speakerJson;
    this.talkTimeJson = talkTimeJson;
    this.totalSeats = totalSeats;
    this.version = version;
  }

  public Long getId() {
    return id;
  }

  public int getTotalSeats() {
    return totalSeats;
  }

  public int getReservedSeats() {
    return reservedSeats;
  }

  public String getName() {
    return name;
  }

  public Long getVersion() {
    return version;
  }

  public static TalkEntity createWithNameAndTotalSeats(String name, int totalSeats) {
    return new TalkEntity(name, totalSeats);
  }

  public static Talk toDomain(TalkEntity talkEntity) {
    CreationResult<Talk> talkCreationResult =
        Talk.of(
            new TalkId(talkEntity.id),
            new Speaker(
                new FirstName(talkEntity.speakerJson.firstName()),
                new LastName(talkEntity.speakerJson.lastName())),
            talkEntity.name,
            new TalkTime(
                new StartTime(talkEntity.talkTimeJson.startTime()),
                new EndTime(talkEntity.talkTimeJson.endTime())),
            talkEntity.totalSeats,
            talkEntity.reservedSeats,
            Version.of(talkEntity.version));

    return switch (talkCreationResult) {
      case CreationResult.Success<Talk> success -> success.value();
      case CreationResult.Failure<Talk> failure ->
          throw new IllegalStateException("Unexpected value: " + failure);
    };
  }

  public static TalkEntity from(AddTalk addTalk) {
    return new TalkEntity(
        addTalk.talkName().value(),
        new SpeakerJson(
            addTalk.speaker().firstName().value(), addTalk.speaker().lastName().value()),
        new TalkTimeJson(
            addTalk.talkTime().startTime().value(), addTalk.talkTime().endTime().value()),
        addTalk.seatCount().value());
  }

  public static TalkEntity from(UpdateTalk updateTalk, int reservedSeats) {
    return new TalkEntity(
        updateTalk.id().value(),
        updateTalk.talkName().value(),
        new SpeakerJson(
            updateTalk.speaker().firstName().value(), updateTalk.speaker().lastName().value()),
        new TalkTimeJson(
            updateTalk.talkTime().startTime().value(), updateTalk.talkTime().endTime().value()),
        updateTalk.seatCount().value(),
        reservedSeats,
        updateTalk.version().value());
  }

  public static TalkEntity from(Talk talk) {
    return new TalkEntity(
        talk.id().value(),
        talk.talkName().value(),
        new SpeakerJson(talk.speaker().firstName().value(), talk.speaker().lastName().value()),
        new TalkTimeJson(talk.talkTime().startTime().value(), talk.talkTime().endTime().value()),
        talk.seatCount().value(),
        talk.reservedSeats().value(),
        talk.version().value());
  }
}
