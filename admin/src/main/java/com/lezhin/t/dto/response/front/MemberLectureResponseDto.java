package com.lezhin.t.dto.response.front;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MemberLectureResponseDto {

    @Getter
    @Setter
    @Builder
    public static class MemberLecture implements Serializable {
        private long memberId;
        private long lectureId;
    }

    @Getter
    @Setter
    @Builder
    public static class Lecture {
        private long lectureId;
        private LocalDateTime startDt;
        private LocalDateTime endDt;
        private long applyCount;
        private LocalTime runtime;

    }
}
