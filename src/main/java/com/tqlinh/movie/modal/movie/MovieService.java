package com.tqlinh.movie.modal.movie;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.exception.OperationNotPermittedException;
import com.tqlinh.movie.modal.genre.Genre;
import com.tqlinh.movie.modal.genre.GenreRepository;
import com.tqlinh.movie.modal.movie.*;
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
public class MovieService {
    public final MovieMapper movieMapper;
    public final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public Integer create(MovieRequest request, Authentication connectedUser) {
        List<Genre> genres = findByIds(request.genreIds());
        User user = (User) connectedUser.getPrincipal();
        Movie movie = movieMapper.toMovie(request, genres);
        movie.setOwner(user);
        return movieRepository.save(movie).getId();
    }

    public Integer update(Integer id, MovieRequest request, Authentication connectedUser) {
       Movie movie = movieRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Movie not found"));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(user.getId(), movie.getOwner().getId())) {
            throw new OperationNotPermittedException("You cannot update others movieId status");
        }
        List<Genre> genres = findByIds(request.genreIds());
        movie.setGenre(genres);
        return movieRepository.save(movieMapper.setMovie(movie, request)).getId();
    }

    public void delete(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found movieId rate"));
         movieRepository.delete(movie);
    }

    public MovieResponse findById(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found movieId rate"));
        return movieMapper.toResponse(movie);

    }

    public PageResponse<MovieResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Movie> movie = movieRepository.findAll(pageable);
        List<MovieResponse> responses = movie.stream()
                .map(movieMapper::toResponse)
                .toList();
        return new PageResponse<>(
                responses,
                movie.getNumber(),
                movie.getSize(),
                movie.getTotalElements(),
                movie.getTotalPages(),
                movie.isFirst(),
                movie.isLast()
        );
    }

    public List<Genre> findByIds(List<Integer> genreIds) {
        List<Genre> genres = new ArrayList<>();
        if (genreIds != null) {
            for (Integer genreId : genreIds) {
                Genre genre = genreRepository.findById(genreId)
                        .orElseThrow(() -> new RuntimeException("Genre not found"));
                genres.add(genre);
            }
        }
        return genres;
    }
}
