package com.lezhin.t.entity;

import com.lezhin.t.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "tbLecture")
@ToString
@EntityListeners({AuditingEntityListener.class})
@NoArgsConstructor
public class LectureEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;

    private String speaker;

    private LocalDateTime startDt;

    private LocalDateTime endDt;

    private LocalTime runtime;

    private long applyCount;

    private String contents;

    @Builder
    public LectureEntity(Long roomId, String speaker, LocalDateTime startDt, LocalDateTime endDt, LocalTime runtime, long applyCount, String contents) {
        this.roomId = roomId;
        this.speaker = speaker;
        this.startDt = startDt;
        this.endDt = endDt;
        this.runtime = runtime;
        this.applyCount = applyCount;
        this.contents = contents;
    }
}
