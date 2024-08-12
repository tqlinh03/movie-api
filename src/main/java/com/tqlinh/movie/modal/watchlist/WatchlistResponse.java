package com.tqlinh.movie.modal.watchlist;

import com.tqlinh.movie.modal.movie.Movie;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class WatchlistResponse {
    private List<Movie> movies;
}
