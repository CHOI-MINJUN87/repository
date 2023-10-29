package com.lezhin.t.service;


import com.lezhin.t.dto.request.LectureRequestDto;
import com.lezhin.t.dto.response.LectureResponseDto;
import com.lezhin.t.service.front.MemberLectureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("등록> 조회> 신청 테스트")
@SpringBootTest
public class LectureServiceTest {

    @Autowired
    LectureService lectureService;

    @Autowired
    MemberLectureService memberLectureService;

    @DisplayName("강의 등록")
    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void addLecture(long applyCount) {
        // 등록
        LectureResponseDto.LectureDto lectureDto = lectureService.addLecture(LectureRequestDto.LectureDto.builder()
                .startDt(LocalDateTime.now())
                .applyCount(applyCount)
                .build());

        assertThat(applyCount).isEqualTo(lectureDto.getApplyCount());
    }

    @Test
    @DisplayName("전체 강의 조회")
    void selectLecture() {
        lectureService.addLecture(LectureRequestDto.LectureDto.builder()
                .startDt(LocalDateTime.now())
                .applyCount(10L)
                .build());

        lectureService.addLecture(LectureRequestDto.LectureDto.builder()
                .startDt(LocalDateTime.now())
                .applyCount(10L)
                .build());

        List<LectureResponseDto.LectureDto> lectureDtoList = lectureService.selectLecture();

        assertThat(2).isEqualTo(lectureDtoList.size());
    }

    @Test
    @DisplayName("신청 강의 조회")
    void selectLectureApplyMember() {
        LectureResponseDto.LectureDto lectureDto = lectureService.addLecture(LectureRequestDto.LectureDto.builder()
                .startDt(LocalDateTime.now())
                .applyCount(10L)
                .build());

        memberLectureService.applyLecture(12345, lectureDto.getId());
        memberLectureService.applyLecture(12346, lectureDto.getId());

        List<LectureResponseDto.LectureApplyMemberDto> lectureApplyMemberDtoList = lectureService.selectLectureApplyMember(lectureDto.getId());

        assertThat(2).isEqualTo(lectureApplyMemberDtoList.size());
    }
}
