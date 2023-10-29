package com.lezhin.t.service.front;

import com.lezhin.t.dto.response.front.LectureRankingResponseDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class LectureRankingServiceImpl implements LectureRankingService {

    private final RedisTemplate<String, String> redisTemplate;

    private final static String BASE_REDIS_KEY = "ranking::lecture::";

    public LectureRankingServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<LectureRankingResponseDto.LectureRanking> selectLectureRanking() {

        Set<String> keys = redisTemplate.keys(BASE_REDIS_KEY + "*");

        HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();

        for (String i : keys) {
            // i.replace("ranking::lecture", ""); // ranking::lecture1::12341 > 1::12341
            String[] data = i.replace(BASE_REDIS_KEY, "").split("::"); // 1::12341
            String k = data[0]; // 1 강의 번호
            String v = data[1]; // 12341 회원 번호

            ArrayList<Integer> arr = hashMap.getOrDefault(k, new ArrayList<>());
            arr.add(Integer.valueOf(v));
            hashMap.put(k, arr);
        }

        List<Map.Entry<String, ArrayList<Integer>>> entries = new ArrayList<>(hashMap.entrySet());
        // 강의별 신청 인원 기준으로 오름 차순 정렬
        entries.sort((o1, o2) -> o2.getValue().size() - o1.getValue().size());

        return entries.stream().map(
                e -> LectureRankingResponseDto.LectureRanking.builder()
                        .lectureDetailId(e.getKey() != null ? Long.valueOf(e.getKey()) : null)
                        .count(e.getValue().size())
                        .build()
        ).toList();
    }
}
