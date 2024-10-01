package com.tqlinh.movie.modal.episode;

import com.tqlinh.movie.modal.view.View;
import com.tqlinh.movie.modal.view.ViewResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EpisodeResponse {
    private String title;
    private Integer episodeNumber;
    private LocalDateTime releaseDateTime;
    private String movieUrl;
    private Integer point;
    private LocalDateTime payUntil;
    private ViewResponse view;
}
