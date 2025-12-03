package com.sherwin.conference.bookingsystem.repository;

import com.sherwin.conference.bookingsystem.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  @Query("SELECT t FROM Ticket t WHERE t.talkId = :talkId AND t.status = :status")
  List<Ticket> findByTalkIdAndStatus(@Param("talkId") Long talkId, @Param("status") String status);
  // This query will cause N+1 hell in lists
}
