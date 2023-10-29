package com.lezhin.t.entity;


import com.lezhin.t.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "tbLectureApply")
@IdClass(LectureApplyEntity.LectureApplyPk.class)
@EntityListeners({AuditingEntityListener.class})
@NoArgsConstructor
public class LectureApplyEntity extends BaseEntity {
    @Id
    private Long memberId;
    @Id
    private Long lectureDetailId;


    @Builder
    public LectureApplyEntity(Long memberId, Long lectureDetailId) {
        this.memberId = memberId;
        this.lectureDetailId = lectureDetailId;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LectureApplyPk implements Serializable {
        private Long memberId;
        private Long lectureDetailId;
    }
}
