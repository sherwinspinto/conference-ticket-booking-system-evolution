package com.sherwin.conference.bookingsystem.domain.feature.model;

import com.sherwin.conference.bookingsystem.domain.feature.commons.validations.FieldError.ErrorParams;

public interface MetaData {

  interface PathProvider {
    String getPath();

    default ErrorParams.FieldName toFieldName() {
      return ErrorParams.FieldName.of(getPath());
    }

    /** Anchors this relative path to a parent path. */
    default PathProvider at(PathProvider parent) {
      return () -> parent.getPath() + "." + getPath();
    }
  }
}
