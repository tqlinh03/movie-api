package com.tqlinh.movie.modal.movie;


import java.util.List;

public record MovieRequest(
         String title,
         String description,
         String thumbnailUrl,
         String director,
         String cast,
         List<Integer> genreIds
) {
}
