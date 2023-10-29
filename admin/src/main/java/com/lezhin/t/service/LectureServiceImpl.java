package com.lezhin.t.service;

import com.lezhin.t.dto.request.LectureRequestDto;
import com.lezhin.t.dto.response.LectureResponseDto;
import com.lezhin.t.entity.LectureEntity;
import com.lezhin.t.entity.repository.LectureApplyRepository;
import com.lezhin.t.entity.repository.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final LectureApplyRepository lectureApplyRepository;

    public LectureServiceImpl(LectureRepository lectureRepository, LectureApplyRepository lectureApplyRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureApplyRepository = lectureApplyRepository;
    }

    @Override
    public List<LectureResponseDto.LectureDto> selectLecture() {
        return lectureRepository.findAllByOrderByIdDesc().stream()
                .map(lectureEntity ->
                    LectureResponseDto.LectureDto.builder()
                            .startDt(lectureEntity.getStartDt())
                            .endDt(lectureEntity.getEndDt())
                            .id(lectureEntity.getId())
                            .applyCount(lectureEntity.getApplyCount())
                            .runtime(lectureEntity.getRuntime())
                            .contents(lectureEntity.getContents())
                            .speaker(lectureEntity.getSpeaker())
                            .roomId(lectureEntity.getRoomId())
                            .build()
                )
                .toList();
    }

    @Override
    public LectureResponseDto.LectureDto addLecture(LectureRequestDto.LectureDto lectureDto) {
        LectureEntity lectureEntity = lectureRepository.save(lectureDto.getEntity());

        return LectureResponseDto.LectureDto.builder()
                .id(lectureEntity.getId())
                .roomId(lectureEntity.getRoomId())
                .endDt(lectureEntity.getEndDt())
                .runtime(lectureEntity.getRuntime())
                .contents(lectureEntity.getContents())
                .speaker(lectureEntity.getSpeaker())
                .applyCount(lectureEntity.getApplyCount())
                .startDt(lectureEntity.getStartDt())
                .build();
    }

    @Override
    public List<LectureResponseDto.LectureApplyMemberDto> selectLectureApplyMember(long lectureId) {
        return lectureApplyRepository.findByLectureDetailId(lectureId).stream()
                .map(lectureApplyEntity ->
                        LectureResponseDto.LectureApplyMemberDto.builder()
                                .memberId(lectureApplyEntity.getMemberId())
                                .LectureDetailId(lectureApplyEntity.getLectureDetailId())
                                .build()
                ).toList();
    }
}
