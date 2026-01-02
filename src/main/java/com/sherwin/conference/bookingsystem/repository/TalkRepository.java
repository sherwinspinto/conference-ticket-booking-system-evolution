package com.sherwin.conference.bookingsystem.repository;

import com.sherwin.conference.bookingsystem.entity.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalkRepository extends JpaRepository<TalkEntity, Long> {
}
