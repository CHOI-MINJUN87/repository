package com.lezhin.t.controller;


import com.lezhin.t.dto.request.LectureRequestDto;
import com.lezhin.t.dto.response.LectureResponseDto;
import com.lezhin.t.response.LezhinResponse;
import com.lezhin.t.service.LectureService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/a")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @Operation(summary = "강연목록 (전체강연목록)")
    @GetMapping("/lectures")
    public LezhinResponse<List<LectureResponseDto.LectureDto>> getLecture() {
        return LezhinResponse.<List<LectureResponseDto.LectureDto>>builder()
                .data(lectureService.selectLecture())
                .build();
    }

    @Operation(summary = "강연등록(강연자,강연장,신청인원,강연시간,강연내용 입력)")
    @PostMapping("/lectures")
    public void postLecture(@RequestBody LectureRequestDto.LectureDto lectureDto) {
        log.info(lectureDto.toString());
        lectureService.addLecture(lectureDto);
    }

    @Operation(summary = "신청자 목록(강연별 신청한 사번 목록)")
    @GetMapping("/lectures/{lectureId}/members")
    public LezhinResponse<List<LectureResponseDto.LectureApplyMemberDto>> getLectureApplyMember(
            @PathVariable Long lectureId) {

        log.info(String.format("%d", lectureId));
        return LezhinResponse.<List<LectureResponseDto.LectureApplyMemberDto>>builder()
                .data(lectureService.selectLectureApplyMember(lectureId))
                .build();
    }
}
