package com.astrolog.backend.repository;

import com.astrolog.backend.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
    List<Observation> findByLocationOrderByCreatedAtDesc(String location);
    List<Observation> findByTargetOrderByCreatedAtDesc(String target);
    List<Observation> findAllByOrderByCreatedAtDesc();
}

