package com.sherwin.conference.bookingsystem.repository;

import com.sherwin.conference.bookingsystem.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
  @Query(value = "SELECT * FROM tickets t WHERE t.talk_id = :talkId AND t.status = :status", nativeQuery = true)
  List<TicketEntity> findByTalkIdAndStatus(@Param("talkId") Long talkId, @Param("status") String status);
  // This query will cause N+1 hell in lists
}
