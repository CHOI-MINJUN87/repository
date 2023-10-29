package com.lezhin.t.controller.front;

import com.lezhin.t.dto.response.front.LectureRankingResponseDto;
import com.lezhin.t.response.LezhinResponse;
import com.lezhin.t.service.front.LectureRankingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/f")
public class LectureRankingController {

    private final LectureRankingService lectureRankingService;

    public LectureRankingController(LectureRankingService lectureRankingService) {
        this.lectureRankingService = lectureRankingService;
    }

    @Operation(summary = "실시간 인기 강연")
    @GetMapping("/lecture/ranking")
    public LezhinResponse<List<LectureRankingResponseDto.LectureRanking>> getLectureRanking() {
        return LezhinResponse.<List<LectureRankingResponseDto.LectureRanking>>builder()
                .data(lectureRankingService.selectLectureRanking())
                .build();

    }
}
