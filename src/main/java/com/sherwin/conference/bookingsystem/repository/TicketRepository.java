package com.sherwin.conference.bookingsystem.repository;

import com.sherwin.conference.bookingsystem.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  List<Ticket> findByTalkIdAndStatus(Long talkId, String status);
  // This query will cause N+1 hell in lists
}
