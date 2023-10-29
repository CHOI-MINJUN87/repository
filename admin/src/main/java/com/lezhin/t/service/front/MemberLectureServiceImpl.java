package com.lezhin.t.service.front;


import com.lezhin.t.config.RedisLockRepository;
import com.lezhin.t.dto.response.front.MemberLectureResponseDto;
import com.lezhin.t.entity.LectureApplyEntity;
import com.lezhin.t.entity.LectureEntity;
import com.lezhin.t.entity.repository.LectureApplyRepository;
import com.lezhin.t.entity.repository.LectureRepository;
import com.lezhin.t.exception.LezhinErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@Service
@Transactional
public class MemberLectureServiceImpl implements MemberLectureService {

    private final LectureApplyRepository lectureApplyRepository;
    private final LectureRepository lectureRepository;
    private final RedisLockRepository redisLockRepository;

    private static final String APPLY_LECTURE = "ranking::lecture";

    public MemberLectureServiceImpl(LectureApplyRepository lectureApplyRepository, LectureRepository lectureRepository, RedisLockRepository redisLockRepository) {
        this.lectureApplyRepository = lectureApplyRepository;
        this.lectureRepository = lectureRepository;
        this.redisLockRepository = redisLockRepository;
    }


    @Cacheable(value = APPLY_LECTURE, key = "#lectureId+'::'+#memberId", cacheManager = "applyLectureManager")
    @Override
    public void applyLecture(long memberId, long lectureId) {

        try {
            while (!redisLockRepository.lock(APPLY_LECTURE + lectureId)) {
                Thread.sleep(100L);
            }

            LectureEntity lecture = lectureRepository.findById(lectureId)
                    .orElseThrow(() -> new LezhinErrorException("강연 정보를 찾을 수 없습니다."));

            if (lecture.getApplyCount() <= 0) {
                throw new LezhinErrorException("신청 가능인원을 초과했습니다.");
            }

            lecture.setApplyCount(lecture.getApplyCount() - 1);

            lectureApplyRepository.save(
                    LectureApplyEntity.builder()
                            .lectureDetailId(lectureId)
                            .memberId(memberId)
                            .build()
            );
        } catch (InterruptedException e) {
            throw new LezhinErrorException("에러");
        } finally {
            redisLockRepository.unlock(APPLY_LECTURE + lectureId);
        }
    }

    @Override
    public List<MemberLectureResponseDto.Lecture> selectAvailableLecture() {
        LocalDateTime now = LocalDateTime.now();

        log.info("startDt : {}", now.minusDays(7).format(DateTimeFormatter.ISO_DATE));
        log.info("startDt : {}", now.plusDays(2).format(DateTimeFormatter.ISO_DATE));

        return lectureRepository.findByStartDtBetween(now.minusDays(7), now.plusDays(2)).stream()
                .map(lectureEntity ->
                        MemberLectureResponseDto.Lecture.builder()
                                .lectureId(lectureEntity.getId())
                                .applyCount(lectureEntity.getApplyCount())
                                .endDt(lectureEntity.getEndDt())
                                .runtime(lectureEntity.getRuntime())
                                .startDt(lectureEntity.getStartDt())
                                .runtime(lectureEntity.getRuntime())
                                .build()
                ).toList();
    }

    @Override
    public List<MemberLectureResponseDto.MemberLecture> selectMemberLecture(long memberId) {

        return lectureApplyRepository.findByMemberId(memberId).stream().map(
                lectureApplyEntity ->
                        MemberLectureResponseDto.MemberLecture.builder()
                                .lectureId(lectureApplyEntity.getLectureDetailId())
                                .memberId(lectureApplyEntity.getMemberId())
                                .build()
        ).toList();
    }

    @Override
    public boolean existsApplyLecture(long memberId, long lectureId) {
        return lectureApplyRepository.existsById(
                LectureApplyEntity.LectureApplyPk.builder()
                        .memberId(memberId)
                        .lectureDetailId(lectureId)
                        .build());
    }

    @Override
    public void deleteMemberLecture(long memberId, long lectureId) {
        lectureApplyRepository.deleteById(
                LectureApplyEntity.LectureApplyPk.builder()
                        .memberId(memberId)
                        .lectureDetailId(lectureId)
                        .build());
    }
}
