package com.sherwin.conference.bookingsystem.infrastructure.db.respository;

import com.sherwin.conference.bookingsystem.infrastructure.db.entity.ticket.TicketEntity2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository2 extends JpaRepository<TicketEntity2, Long> {
  String UPDATE_TICKET_STATUS_TO_EXPIRED_SQL = """
      UPDATE TICKETS
         SET status = 'EXPIRED'
       WHERE
         id = :id
         AND status != 'EXPIRED'
      """;

  @Modifying
  @Query(value = UPDATE_TICKET_STATUS_TO_EXPIRED_SQL, nativeQuery = true)
  int updateStatusToExpired(Long id);

  default Optional<TicketEntity2> updateToExpiredAndFetch(Long id) {
    if(updateStatusToExpired(id) != 1) {
      return Optional.empty();
    }
    return findById(id);
  }
}
