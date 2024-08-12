package com.tqlinh.movie.modal.watchlist;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.exception.OperationNotPermittedException;
import com.tqlinh.movie.modal.genre.Genre;
import com.tqlinh.movie.modal.movie.Movie;
import com.tqlinh.movie.modal.movie.MovieRepository;
import com.tqlinh.movie.modal.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WatchlistService {
    public final WatchlistMapper watchlistMapper;
    public final WatchlistRepository watchlistRepository;
    private final MovieRepository movieRepository;

    public Integer addWatchlist(WatchlistRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Watchlist watchlist = watchlistRepository.findById(user.getWatchlist().getId())
                .orElseThrow(() -> new RuntimeException(("Not found watchlist")));
        List<Movie> movies = findByIds(request.movieIds());
        watchlist.setMovies(movies);
        return watchlistRepository.save(watchlist).getId();
    }

    public void delete(Integer id, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        if(!Objects.equals(id, user.getWatchlist().getId())) {
            throw new OperationNotPermittedException("You cannot delete watchlists that are not yours");
        }
        Watchlist watchlist = watchlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found watchlist"));
         watchlistRepository.delete(watchlist);
    }

    public PageResponse<WatchlistResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Watchlist> watchlist = watchlistRepository.findAll(pageable);
        List<WatchlistResponse> responses = watchlist.stream()
                .map(watchlistMapper::toResponse)
                .toList();
        return new PageResponse<>(
                responses,
                watchlist.getNumber(),
                watchlist.getSize(),
                watchlist.getTotalElements(),
                watchlist.getTotalPages(),
                watchlist.isFirst(),
                watchlist.isLast()
        );
    }
    public List<Movie> findByIds(List<Integer> movieIds) {
        List<Movie> movies = new ArrayList<>();
        if (movieIds != null) {
            for (Integer watchlistId : movieIds) {
                Movie  movie = movieRepository.findById(watchlistId)
                        .orElseThrow(() -> new RuntimeException("movieId not found"));
                movies.add(movie);
            }
        }
        return movies;
    }
}
