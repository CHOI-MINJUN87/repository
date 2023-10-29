package com.lezhin.t.dto.response.front;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

public class LectureRankingResponseDto {

    @Getter
    @Setter
    @ToString
    @Builder
    public static class LectureRanking {
        private Long lectureDetailId;
        private int count;
    }
}
