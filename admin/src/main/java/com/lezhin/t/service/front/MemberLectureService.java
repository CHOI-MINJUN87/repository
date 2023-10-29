package com.lezhin.t.service.front;

import com.lezhin.t.dto.response.front.MemberLectureResponseDto;

import java.util.List;

public interface MemberLectureService {

    void applyLecture(long memberId, long lectureId);

    List<MemberLectureResponseDto.Lecture> selectAvailableLecture();

    List<MemberLectureResponseDto.MemberLecture> selectMemberLecture(long memberId);

    boolean existsApplyLecture(long memberId, long lectureId);

    void deleteMemberLecture(long memberId, long lectureId);
}
