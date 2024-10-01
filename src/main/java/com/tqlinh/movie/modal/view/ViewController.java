package com.tqlinh.movie.modal.view;

import com.tqlinh.movie.modal.movie.MovieResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/view")
@AllArgsConstructor
public class ViewController {
    public final ViewService viewService;

    @PatchMapping
    public ResponseEntity<Integer> addView(
            @RequestBody @Valid ViewResquest viewResquest,
            Authentication connectedUser
    ) {

        return ResponseEntity.ok(viewService.addView(viewResquest, connectedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewResponse> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(viewService.findById(id));
    }
}
