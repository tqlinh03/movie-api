package com.tqlinh.movie.modal.movie;

import com.tqlinh.movie.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    public final MovieService movieService;

    @PostMapping
    public ResponseEntity<Integer> create(
            @RequestBody @Valid MovieRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok((movieService.create(request, connectedUser)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> update(
            @RequestBody @Valid MovieRequest request,
            @PathVariable("id") Integer id,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(movieService.update(id, request, connectedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        movieService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<MovieResponse>> findAll(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "0", required = false) int size

    ) {
        return ResponseEntity.ok(movieService.findAll(page, size));
    }
}
