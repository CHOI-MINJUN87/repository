package com.lezhin.t.dto.request;

import com.lezhin.t.entity.LectureEntity;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LectureRequestDto {

    @Getter
    @Setter
    @ToString
    @Builder
    public static class LectureDto {

        @Schema(description = "강의자", example = "강사입니다.")
        private String speaker;

        @Schema(description = "강의실번호", example = "12345")
        private Long roomId;

        @Schema(description = "강의 시작일", example = "2023-10-28T11:11:11", type = "string")
        private LocalDateTime startDt;

        @Schema(description = "강의 종료일", example = "2022-10-28T13:11:11", type = "string")
        private LocalDateTime endDt;

        @Schema(description = "런타임", example = "01:00:00", type = "string")
        private LocalTime runtime;

        @Schema(description = "신청 가능 인원", example = "3")
        private Long applyCount;

        @Schema(description = "강의 내용", example = "아브라카타브라")
        private String contents;

        @Hidden
        public LectureEntity getEntity() {
            return LectureEntity.builder()
                    .runtime(runtime)
                    .applyCount(applyCount == null ? 0 : applyCount)
                    .startDt(startDt)
                    .endDt(endDt)
                    .contents(contents)
                    .speaker(speaker)
                    .roomId(roomId)
                    .build();
        }
    }
}
