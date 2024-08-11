package com.tqlinh.movie.modal.movie;

import com.tqlinh.movie.modal.episode.Episode;
import com.tqlinh.movie.modal.episode.EpisodeResponse;
import com.tqlinh.movie.modal.genre.Genre;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MovieResponse {
    String title;
    String description;
    String thumbnailUrl;
    String director;
    String cast;
    Integer userid;
    List<Genre> genres;
    List<Episode> episodes;
}
