package com.lezhin.t.service.front;

import com.lezhin.t.dto.request.LectureRequestDto;
import com.lezhin.t.dto.response.LectureResponseDto;
import com.lezhin.t.dto.response.front.MemberLectureResponseDto;
import com.lezhin.t.exception.LezhinErrorException;
import com.lezhin.t.service.LectureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@Transactional
@SpringBootTest
class MemberLectureServiceTest {

    @Autowired
    MemberLectureService memberLectureService;

    @Autowired
    LectureService lectureService;


    private static final long MEMBER_ID = 12345;

    @Nested
    @DisplayName("강연 신청")
    class applyLecture {
        @Test
        @DisplayName("강연 정보 못찾음")
        void non() {
            Throwable thrown = catchThrowable(() -> {
                memberLectureService.applyLecture(MEMBER_ID, 5555);
            });

            assertThat(thrown)
                    .isInstanceOf(LezhinErrorException.class)
                    .hasMessageContaining("강연 정보를 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("신청 가능한 강연 조회")
        void selectAvailableLecture() {
            LocalDateTime now = LocalDateTime.now();
            // 지난 기간
            lectureService.addLecture(
                    LectureRequestDto.LectureDto.builder()
                            .startDt(now.minusDays(10))
                            .applyCount(10L)
                            .build()
            );
            // 기간 해당
            lectureService.addLecture(
                    LectureRequestDto.LectureDto.builder()
                            .startDt(now)
                            .applyCount(10L)
                            .build()
            );
            // 이후 기간
            lectureService.addLecture(
                    LectureRequestDto.LectureDto.builder()
                            .startDt(now.plusDays(10))
                            .applyCount(10L)
                            .build()
            );

            List<MemberLectureResponseDto.Lecture> lectures = memberLectureService.selectAvailableLecture();
            // 노출 중인 강연 1개
            assertThat(1).isEqualTo(lectures.size());
        }

        @Test
        @DisplayName("강연 신청")
        void apply() {
            // 기간 해당
            lectureService.addLecture(
                    LectureRequestDto.LectureDto.builder()
                            .startDt(LocalDateTime.now())
                            .applyCount(10L)
                            .build()
            );

            // 기간 해당
            lectureService.addLecture(
                    LectureRequestDto.LectureDto.builder()
                            .startDt(LocalDateTime.now())
                            .applyCount(20L)
                            .build()
            );

            // 신청 가능한 강연 목록
            List<MemberLectureResponseDto.Lecture> lectures = memberLectureService.selectAvailableLecture();

            for (MemberLectureResponseDto.Lecture lecture : lectures) {
                // 강연 등록 에러 확인
                assertDoesNotThrow(() -> {
                    memberLectureService.applyLecture(MEMBER_ID, lecture.getLectureId());
                });
            }
        }

        @Test
        @DisplayName("강연 취소")
        void deleteMemberLecture() {
            // 사전 강연 등록 lectureId : 1 로 등록됨
            LectureResponseDto.LectureDto lectureDto = lectureService.addLecture(LectureRequestDto.LectureDto.builder()
                    .startDt(LocalDateTime.now())
                    .applyCount(10L)
                    .build());
            // 1번 강연 신청
            memberLectureService.applyLecture(MEMBER_ID, lectureDto.getId());
            // 강연 신청 삭제
            memberLectureService.deleteMemberLecture(MEMBER_ID, lectureDto.getId());

            // 삭제 강연 확인
            assertThat(memberLectureService.existsApplyLecture(MEMBER_ID, lectureDto.getId())).isFalse();
        }
    }
}