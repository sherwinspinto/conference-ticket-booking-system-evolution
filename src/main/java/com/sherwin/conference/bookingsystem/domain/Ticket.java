package com.sherwin.conference.bookingsystem.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long talkId;
  private String userEmail;
  private String status; // "RESERVED", "PAID", "EXPIRED"
  private LocalDateTime reservedAt;
  private LocalDateTime expiresAt;

  // Constructors, getters, setters – the anemic anti-pattern we'll eviscerate
  public Ticket() {}

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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDateTime getReservedAt() {
    return reservedAt;
  }

  public void setReservedAt(LocalDateTime reservedAt) {
    this.reservedAt = reservedAt;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }
}
