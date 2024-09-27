package com.tqlinh.movie.modal.episode;

import com.tqlinh.movie.modal.genre.Genre;
import com.tqlinh.movie.modal.genre.GenreRequest;
import com.tqlinh.movie.modal.genre.GenreResponse;
import org.springframework.stereotype.Service;

@Service
public class EpisodeMapper {
    public Episode toEpisode(EpisodeRequest request) {
        return Episode.builder()
                .title(request.title())
                .episodeNumber(request.episodeNumber())
                .releaseDateTime(request.releaseDateTime())
                .movieUrl(request.movieUrl())
                .point(request.point())
                .payUntil(request.payUntil())
                .build();
    }

    public EpisodeResponse toResponse(Episode episode) {
        return EpisodeResponse.builder()
                .title(episode.getTitle())
                .episodeNumber(episode.getEpisodeNumber())
                .releaseDateTime(episode.getReleaseDateTime())
                .movieUrl(episode.getMovieUrl())
                .point(episode.getPoint())
                .payUntil(episode.getPayUntil())
                .build();
    }

    public Episode setEpisode(Episode episode, EpisodeRequest request) {
        episode.setTitle(request.title());
        episode.setEpisodeNumber(request.episodeNumber());
        episode.setReleaseDateTime(request.releaseDateTime());
        episode.setMovieUrl(request.movieUrl());
        episode.setPoint(request.point());
        episode.setPayUntil(request.payUntil());
        return episode;
    }

}
