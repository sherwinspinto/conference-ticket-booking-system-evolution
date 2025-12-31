package com.sherwin.conference.bookingsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class TicketEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long talkId;
  private String userEmail;
  private ReservationResult status; // "RESERVED", "PAID", "EXPIRED"
  private LocalDateTime reservedAt;

  // Constructors, getters, setters – the anemic anti-pattern we'll eviscerate
  public TicketEntity() {}

  public TicketEntity(
      Long talkId, String userEmail, ReservationResult status, LocalDateTime reservedAt) {
    this.talkId = talkId;
    this.userEmail = userEmail;
    this.status = status;
    this.reservedAt = reservedAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTalkId() {
    return talkId;
  }

  public void setTalkId(Long talkId) {
    this.talkId = talkId;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public ReservationResult getStatus() {
    return status;
  }

  public void setStatus(ReservationResult status) {
    this.status = status;
  }

  public LocalDateTime getReservedAt() {
    return reservedAt;
  }

  public void setReservedAt(LocalDateTime reservedAt) {
    this.reservedAt = reservedAt;
  }
}
