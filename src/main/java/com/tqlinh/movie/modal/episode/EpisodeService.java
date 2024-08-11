package com.tqlinh.movie.modal.episode;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.movie.Movie;
import com.tqlinh.movie.modal.movie.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    public final EpisodeMapper episodeMapper;
    public final EpisodeRepository episodeRepository;
    private final MovieRepository movieRepository;


    public Integer create(EpisodeRequest request) {
        Movie movie = movieRepository.findById(request.movieId())
                .orElseThrow(() -> new RuntimeException("Not found movie"));
        Episode episode = episodeMapper.toEpisode(request);
        episode.setMovie(movie);
        return episodeRepository.save(episode).getId();
    }

    public Integer update(Integer id, EpisodeRequest request) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found episode"));
        return episodeRepository.save(episodeMapper.setEpisode(episode, request)).getId();
    }

    public void delete(Integer id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found episode"));
         episodeRepository.delete(episode);
    }

    public EpisodeResponse findById(Integer id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found episode rate"));
        return episodeMapper.toResponse(episode);

    }

    public PageResponse<EpisodeResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Episode> episode = episodeRepository.findAll(pageable);
        List<EpisodeResponse> responses = episode.stream()
                .map(episodeMapper::toResponse)
                .toList();
        return new PageResponse<>(
                responses,
                episode.getNumber(),
                episode.getSize(),
                episode.getTotalElements(),
                episode.getTotalPages(),
                episode.isFirst(),
                episode.isLast()
        );
    }

//    public List<GenreResponse> findAll() {
//        List<GenreResponse> exchangeRateResponsese = episodeRepository.findAll()
//                .stream()
//                .map(episodeMapper::toExchangeRateResponse)
//                .toList();
//        return exchangeRateResponsese;
//
//    }
}
