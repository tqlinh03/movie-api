package com.tqlinh.movie.modal.movie;

import com.tqlinh.movie.modal.genre.Genre;
import com.tqlinh.movie.modal.genre.GenreRequest;
import com.tqlinh.movie.modal.genre.GenreResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieMapper {
    public Movie toMovie(MovieRequest request, List<Genre> genreList) {

        return Movie.builder()
                .title(request.title())
                .description(request.description())
                .director(request.director())
                ._cast(request.cast())
                .thumbnailUrl(request.thumbnailUrl())
                .genre(genreList)
                .build();
    }

    public MovieResponse toResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .cast(movie.get_cast())
                .thumbnailUrl(movie.getThumbnailUrl())
                .genres(movie.getGenre())
                .episodes(movie.getEpisodes())
                .build();
    }

    public Movie setMovie(Movie movie, MovieRequest request) {
        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setDirector(request.director());
        movie.set_cast(request.cast());
        movie.setThumbnailUrl(request.thumbnailUrl());
        return movie;
    }

}
