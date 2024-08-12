package com.tqlinh.movie.modal.watchlist;

import com.tqlinh.movie.modal.movie.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistMapper {
    public Watchlist toWatchlist(List<Movie> movie) {
        return Watchlist.builder()
                .movies(movie)
                .build();
    }

    public WatchlistResponse toResponse(Watchlist watchlist) {
        return WatchlistResponse.builder()
                .movies(watchlist.getMovies())
                .build();
    }

    public Watchlist setWatchlist(Watchlist watchlist, List<Movie> movies) {
        watchlist.setMovies(movies);
        return watchlist;
    }

}
