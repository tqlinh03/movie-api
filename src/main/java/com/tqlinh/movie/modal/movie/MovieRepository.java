package com.tqlinh.movie.modal.movie;

import com.tqlinh.movie.modal.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
