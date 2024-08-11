package com.tqlinh.movie.modal.episode;


import java.time.LocalDateTime;

public record EpisodeRequest(
         String title,
         Integer episodeNumber,
         LocalDateTime releaseDateTime,
         String movieUrl,
         Integer point,
         LocalDateTime payUntil,
         Integer movieId
) {
}
