package com.sherwin.conference.bookingsystem.domain.feature.ticket.model;

import com.sherwin.conference.bookingsystem.domain.feature.model.MetaData;

import static com.sherwin.conference.bookingsystem.domain.feature.model.MetaData.*;

public interface TicketMetaData {
  enum Domain implements PathProvider {
    TICKETS("tickets");
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
    RESERVE_SEAT(Domain.TICKETS, "reserve_seat");

    private final String path;

    Operation(Domain domain, String name) {
      this.path = domain.getPath() + "." + name;
    }

    @Override
    public String getPath() {
      return path;
    }
  }
  enum TicketFields implements PathProvider {
    TALK_ID("talk_id"),
    USER_EMAIL("user_email"),
    ID("id"),
    RESERVATION_STATUS("reservation_status"),
    RESERVED_AT("reserved_at");

    private final String path;

    TicketFields(String path) {
      this.path = path;
    }

    public String getPath() {
      return path;
    }
  }
}
