package com.lezhin.t.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LectureResponseDto {

    @Getter
    @Setter
    @Builder
    public static class LectureDto {
        private long id;
        private Long roomId;
        private String speaker;
        private LocalDateTime startDt;
        private LocalDateTime endDt;
        private long applyCount;
        private LocalTime runtime;
        private String contents;
    }

    @Getter
    @Setter
    @Builder
    public static class LectureApplyMemberDto {
        private long memberId;
        private long LectureDetailId;
    }
}
