package com.sherwin.conference.bookingsystem.infrastructure.repository;

import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalkRepository extends JpaRepository<TalkEntity, Long> {
}
