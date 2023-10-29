package com.lezhin.t.entity.repository;

import com.lezhin.t.entity.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureRepository extends JpaRepository<LectureEntity, Long> {

    List<LectureEntity> findAllByOrderByIdDesc();

    List<LectureEntity> findByStartDtBetween(LocalDateTime startDt, LocalDateTime endDt);
}
