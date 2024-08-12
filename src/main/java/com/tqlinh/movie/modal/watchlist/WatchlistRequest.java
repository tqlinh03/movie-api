package com.tqlinh.movie.modal.watchlist;

import java.util.List;

public record WatchlistRequest(
         List<Integer> movieIds
) {
}
