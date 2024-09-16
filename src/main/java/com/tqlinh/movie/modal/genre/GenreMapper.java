package com.tqlinh.movie.modal.genre;

import com.tqlinh.movie.modal.exchangeRate.ExchangeRate;
import com.tqlinh.movie.modal.exchangeRate.ExchangeRateRequest;
import com.tqlinh.movie.modal.exchangeRate.ExchangeRateResponse;
import org.springframework.stereotype.Service;

@Service
public class GenreMapper {
    public Genre toGenre(GenreRequest request) {
        return Genre.builder()
                .name(request.name())
                .build();
    }

    public GenreResponse toResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    public Genre setGenre(Genre genre, GenreRequest request) {
        genre.setName(request.name());
        return genre;
    }

}
