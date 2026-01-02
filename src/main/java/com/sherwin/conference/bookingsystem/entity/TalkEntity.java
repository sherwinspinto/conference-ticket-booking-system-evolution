package com.sherwin.conference.bookingsystem.entity;

import com.sherwin.conference.bookingsystem.domain.Talk;
import jakarta.persistence.*;

// Talk.java – must be the single source of truth for available seats
// Must contain:
//   - private int totalSeats
//   - private int reservedSeats (or calculated from events later)
//   - @Version Long version   ← optimistic locking
//   - method boolean reserveSeat(String userEmail) → returns true/false
//   - method void confirmSeat(Long ticketId)
//   - method void releaseSeat(Long ticketId)   // for expiry
//   - private constructor + static factory (e.g. Talk.create(...))
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private final String name;

  private final int totalSeats;

  private int reservedSeats = 0;

  @Version private Long version;

  public boolean tryReserveSeat() {
    if (reservedSeats >= totalSeats) return Boolean.FALSE;
    reservedSeats++;
    return Boolean.TRUE;
  }

  public void confirmSeat(Long ticketId) {}

  public void releaseSeat(Long ticketId) {
    if (reservedSeats > 0) reservedSeats--;
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

  public static TalkEntity createWithNameAndTotalSeats(String name, int totalSeats) {
    return new TalkEntity(name, totalSeats);
  }
}
