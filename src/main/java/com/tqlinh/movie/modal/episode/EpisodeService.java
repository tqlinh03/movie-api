package com.tqlinh.movie.modal.episode;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.movie.Movie;
import com.tqlinh.movie.modal.movie.MovieRepository;
import com.tqlinh.movie.modal.point.PointService;
import com.tqlinh.movie.modal.user.User;
import com.tqlinh.movie.modal.vipPackage.VipName;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    public final EpisodeMapper episodeMapper;
    public final EpisodeRepository episodeRepository;
    private final MovieRepository movieRepository;
    private final PointService pointService;


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

    @Transactional
    public EpisodeResponse checkAccessEpisode(Integer episodeId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new RuntimeException("Not found episode"));
        // check vip status
        VipName vipName = user.getVip().getVipPackage().getName();
        LocalDateTime vipEndDate = user.getVip().getVipEndDate();
        if (checkVipStatus(vipName, vipEndDate)) {
            return findById(episodeId);
        }
        // deduct point user and save episode access
        pointService.deductPoints(episode.getPoint(), user);

        return findById(episodeId);
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

    public EpisodeResponse findById(Integer id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found episode rate"));
        return episodeMapper.toResponse(episode);
    }

    public Boolean checkVipStatus(VipName vipName, LocalDateTime vipEndDate) {
        LocalDateTime now = LocalDateTime.now();
        return vipName == VipName.VIP && now.isBefore(vipEndDate);
    }

    public Boolean checkEpisodeAccess(List<Episode> episodes, Integer episodeId) {
        for (Episode episode : episodes) {
            if (Objects.equals(episode.getId(), episodeId)) {
                return true;
            }
        }
        return false;
    }
}
