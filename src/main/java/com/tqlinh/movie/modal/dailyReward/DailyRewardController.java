package com.tqlinh.movie.modal.dailyReward;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.episode.EpisodeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily-eward")
@RequiredArgsConstructor
public class DailyRewardController {
    public final DailyRewardService dailyRewardService;

    @PostMapping
    public ResponseEntity<Integer> create(
            @RequestBody @Valid DailyRewardRequest request
    ) {
        return ResponseEntity.ok((dailyRewardService.create(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> update(
            @RequestBody @Valid DailyRewardRequest request,
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(dailyRewardService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        dailyRewardService.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<PageResponse<DailyRewardResponse>> findAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "0", required = false) int size

    ) {
        return ResponseEntity.ok(dailyRewardService.findAll(page, size));
    }

}
