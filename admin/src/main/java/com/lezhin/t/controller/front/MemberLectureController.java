package com.lezhin.t.controller.front;

import com.lezhin.t.dto.response.front.MemberLectureResponseDto;
import com.lezhin.t.exception.LezhinErrorException;
import com.lezhin.t.response.LezhinResponse;
import com.lezhin.t.service.front.MemberLectureService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/f")
public class MemberLectureController {

    private final MemberLectureService memberLectureService;

    public MemberLectureController(MemberLectureService memberLectureService) {
        this.memberLectureService = memberLectureService;
    }

    @Operation(summary = "강연목록(신청가능)")
    @GetMapping("/lectures")
    public LezhinResponse<List<MemberLectureResponseDto.Lecture>>getLectures() {
        return LezhinResponse.<List<MemberLectureResponseDto.Lecture>>builder()
                .data(memberLectureService.selectAvailableLecture())
                .build();
    }


    @Operation(summary = "강연 신청")
    @PostMapping("/lectures/{lectureId}/members/{memberId}")
    public void postMemberLecture(@PathVariable Long lectureId, @PathVariable long memberId) {
        // 이전 강의 신청 이력 체크
        if (memberLectureService.existsApplyLecture(memberId, lectureId)) {
            throw new LezhinErrorException("이미 신청을 완료한 강연 입니다.");
        };
        // 강의 신청
        memberLectureService.applyLecture(memberId, lectureId);
    }

    @Operation(summary = "신청내역 조회(사번입력)")
    @GetMapping("/members/{memberId}/lectures")
    public LezhinResponse<List<MemberLectureResponseDto.MemberLecture>> getMemberLecture(@PathVariable long memberId) {
        return LezhinResponse.<List<MemberLectureResponseDto.MemberLecture>>builder()
                .data(memberLectureService.selectMemberLecture(memberId))
                .build();
    }

    @Operation(summary = "신청한 강연 취소")
    @DeleteMapping("/lectures/{lectureId}/members/{memberId}")
    public void delMemberLecture(@PathVariable long memberId, @PathVariable long lectureId) {
        memberLectureService.deleteMemberLecture(memberId,lectureId);
    }

//    @GetMapping("/test/{id}")
//    public void test(@PathVariable long id) throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(20);
//        CountDownLatch countDownLatch = new CountDownLatch(100);
//
//        for (int i = 0; i < 100; i++) {
//            executorService.submit(() -> {
//                try {
//                    memberLectureService.applyLecture(12345, id);
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//
//        countDownLatch.await();
//    }
}
