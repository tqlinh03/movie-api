package com.tqlinh.movie.modal.dailyReward;

import com.tqlinh.movie.common.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DailyRewardService {
    public final DailyRewardRepository dailyRewardRepository;
    public final DailyRewardMapper dailyRewardMapper;


    public Integer create(DailyRewardRequest request) {
        DailyReward dailyReward = dailyRewardMapper.toDailyReWard(request);
        return dailyRewardRepository.save(dailyReward).getId();
    }

    public Integer update(Integer id, DailyRewardRequest request) {
        DailyReward dailyReward = dailyRewardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found dailyReward"));
        return dailyRewardRepository.save(dailyRewardMapper.setDailyReward(dailyReward, request)).getId();
    }

    public void delete(Integer id) {
        DailyReward dailyReward = dailyRewardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found dailyReward"));
         dailyRewardRepository.delete(dailyReward);
    }

    public PageResponse<DailyRewardResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<DailyReward> dailyReward = dailyRewardRepository.findAll(pageable);
        List<DailyRewardResponse> responses = dailyReward.stream()
                .map(dailyRewardMapper::toResponse)
                .toList();
        return new PageResponse<>(
                responses,
                dailyReward.getNumber(),
                dailyReward.getSize(),
                dailyReward.getTotalElements(),
                dailyReward.getTotalPages(),
                dailyReward.isFirst(),
                dailyReward.isLast()
        );
    }

    public DailyRewardResponse findById(Integer id) {
        DailyReward dailyReward = dailyRewardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found dailyReward rate"));
        return dailyRewardMapper.toResponse(dailyReward);
    }


}
