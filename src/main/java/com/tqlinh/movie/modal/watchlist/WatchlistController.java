package com.tqlinh.movie.modal.watchlist;

import com.tqlinh.movie.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/watchlist")
@RequiredArgsConstructor
public class WatchlistController {
    public final WatchlistService watchlistService;

    @PostMapping
    public ResponseEntity<Integer> addWatchlist(
            @RequestBody @Valid WatchlistRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok((watchlistService.addWatchlist(request, connectedUser)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id,
            Authentication connectedUser
    ) {
        watchlistService.delete(id, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<WatchlistResponse>> findAll(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "0", required = false) int size

    ) {
        return ResponseEntity.ok(watchlistService.findAll(page, size));
    }
}
