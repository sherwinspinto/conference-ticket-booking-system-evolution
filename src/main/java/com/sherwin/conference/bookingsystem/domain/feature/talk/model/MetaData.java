package com.sherwin.conference.bookingsystem.domain.feature.talk.model;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError;

public interface MetaData {

  interface PathProvider {
    String getPath();

    default FieldError.ErrorParams.FieldName toFieldName() {
      return FieldError.ErrorParams.FieldName.of(getPath());
    }

    /** Anchors this relative path to a parent path. */
    default PathProvider at(PathProvider parent) {
      return () -> parent.getPath() + "." + getPath();
    }
  }

  enum Domain implements PathProvider {
    TALK("talk");
    private final String path;

    Domain(String path) {
      this.path = path;
    }

    @Override
    public String getPath() {
      return path;
    }
  }

  enum Operation implements PathProvider {
    ADD(Domain.TALK, "add_talk"),
    UPDATE(Domain.TALK, "update_talk");

    private final String path;

    Operation(Domain domain, String name) {
      this.path = domain.getPath() + "." + name;
    }

    @Override
    public String getPath() {
      return path;
    }
  }

  enum Speaker implements PathProvider {
    ROOT("speaker"),
    FIRST_NAME("first_name"),
    LAST_NAME("last_name");

    private final String path;

    Speaker(String path) {
      this.path = path;
    }

    @Override
    public String getPath() {
      return path;
    }
  }

  enum TalkTime implements PathProvider {
    ROOT("talk_time"),
    START_TIME("start_time"),
    END_TIME("end_time");

    private final String path;

    TalkTime(String path) {
      this.path = path;
    }

    @Override
    public String getPath() {
      return path;
    }
  }

  enum TalkFields implements PathProvider {
    ID("id"),
    NAME("talk_name"),
    SEAT_COUNT("seat_count"),
    SPEAKER("speaker"),
    TALK_TIME("talk_time"),
    VERSION("version");

    private final String path;

    TalkFields(String path) {
      this.path = path;
    }

    @Override
    public String getPath() {
      return path;
    }
  }
}
