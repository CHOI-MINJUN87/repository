package com.lezhin.t.service.front;

import com.lezhin.t.dto.response.front.LectureRankingResponseDto;

import java.util.List;

public interface LectureRankingService {

    List<LectureRankingResponseDto.LectureRanking> selectLectureRanking();
}
