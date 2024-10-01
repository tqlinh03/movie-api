package com.tqlinh.movie.modal.episode;

import com.tqlinh.movie.modal.genre.Genre;
import com.tqlinh.movie.modal.genre.GenreRequest;
import com.tqlinh.movie.modal.genre.GenreResponse;
import com.tqlinh.movie.modal.view.View;
import com.tqlinh.movie.modal.view.ViewMapper;
import com.tqlinh.movie.modal.view.ViewRepository;
import com.tqlinh.movie.modal.view.ViewResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EpisodeMapper {
    public final ViewMapper viewMapper;
    public final ViewRepository viewRepository;
    public Episode toEpisode(EpisodeRequest request) {
        View view = new View();
        View savedView = viewRepository.save(view);
        return Episode.builder()
                .title(request.title())
                .episodeNumber(request.episodeNumber())
                .releaseDateTime(request.releaseDateTime())
                .movieUrl(request.movieUrl())
                .point(request.point())
                .payUntil(request.payUntil())
                .view(savedView)
                .build();
    }

    public EpisodeResponse toResponse(Episode episode) {
//        ViewResponse views = episode.getView().s;
        return EpisodeResponse.builder()
                .title(episode.getTitle())
                .episodeNumber(episode.getEpisodeNumber())
                .releaseDateTime(episode.getReleaseDateTime())
                .movieUrl(episode.getMovieUrl())
                .point(episode.getPoint())
                .payUntil(episode.getPayUntil())
                .view(viewMapper.toResponse(episode.getView()))
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
