package com.tqlinh.movie.modal.genre;

import com.tqlinh.movie.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
public class GenreController {
    public final GenreService genreService;

    @PostMapping
    public ResponseEntity<Integer> create(
            @RequestBody @Valid GenreRequest request
    ) {
        return ResponseEntity.ok((genreService.create(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> update(
            @RequestBody @Valid GenreRequest request,
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(genreService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        genreService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(genreService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<GenreResponse>> findAll(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "0", required = false) int size

    ) {
        return ResponseEntity.ok(genreService.findAll(page, size));
    }
}
