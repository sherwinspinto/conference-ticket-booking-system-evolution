package com.sherwin.conference.bookingsystem.infrastructure.repository;

import com.sherwin.conference.bookingsystem.entity.ReservationResult;
import com.sherwin.conference.bookingsystem.entity.TicketEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
  @Query(value = "SELECT * FROM tickets t WHERE t.talk_id = :talkId AND t.status = :status", nativeQuery = true)
  List<TicketEntity> findByTalkIdAndStatus(@Param("talkId") Long talkId, @Param("status") String status);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Tickets t SET t.status = :status WHERE t.id = :ticketId", nativeQuery = true)
  int updateStatus(@Param("status") String status, @Param("ticketId") Long ticketId);
}
