package com.lezhin.t.entity.repository;

import com.lezhin.t.entity.LectureApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureApplyRepository extends JpaRepository<LectureApplyEntity, LectureApplyEntity.LectureApplyPk> {

    List<LectureApplyEntity> findByLectureDetailId(long lectureDetailId);

    List<LectureApplyEntity> findByMemberId(Long memberId);
}
