package com.sherwin.conference.bookingsystem.infrastructure.db.respository;

import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TalkRepository extends JpaRepository<TalkEntity, Long> {
  String INCREMENT_RESERVED_SEAT_COUNT_SQL = """
UPDATE TALKS
  SET reserved_seats = reserved_seats + 1
 WHERE
  id = :talkId
 AND reserved_seats < total_seats
""";

  @Modifying
  @Query(value = INCREMENT_RESERVED_SEAT_COUNT_SQL, nativeQuery = true)
  int incrementReservedSeatCount(Long talkId);
}
