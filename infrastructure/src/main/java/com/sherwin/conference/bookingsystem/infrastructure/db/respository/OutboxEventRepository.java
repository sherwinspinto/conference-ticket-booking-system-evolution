package com.sherwin.conference.bookingsystem.infrastructure.db.respository;

import com.sherwin.conference.bookingsystem.infrastructure.db.entity.event.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, Long> {
    List<OutboxEventEntity> findByStatusAndCreatedAtBefore(String status, LocalDateTime threshold);
}
