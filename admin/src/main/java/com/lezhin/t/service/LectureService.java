package com.lezhin.t.service;

import com.lezhin.t.dto.request.LectureRequestDto;
import com.lezhin.t.dto.response.LectureResponseDto;

import java.util.List;

public interface LectureService {

    List<LectureResponseDto.LectureDto> selectLecture();

    LectureResponseDto.LectureDto addLecture(LectureRequestDto.LectureDto lectureRequestDto);

    List<LectureResponseDto.LectureApplyMemberDto> selectLectureApplyMember(long lectureId);
}
