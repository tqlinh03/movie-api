package com.tqlinh.movie.modal.episode;

import com.tqlinh.movie.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/episode")
@RequiredArgsConstructor
public class EpisodeController {
    public final EpisodeService episodeService;

    @PostMapping
    public ResponseEntity<Integer> create(
            @RequestBody @Valid EpisodeRequest request
    ) {
        return ResponseEntity.ok((episodeService.create(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> update(
            @RequestBody @Valid EpisodeRequest request,
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(episodeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        episodeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeResponse> findById(
            @PathVariable("id") Integer episodeId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(episodeService.checkAccessEpisode(episodeId, connectedUser));
    }

    @GetMapping
    public ResponseEntity<PageResponse<EpisodeResponse>> findAll(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "0", required = false) int size

    ) {
        return ResponseEntity.ok(episodeService.findAll(page, size));
    }
}
