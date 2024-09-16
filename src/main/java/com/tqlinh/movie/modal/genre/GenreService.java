package com.tqlinh.movie.modal.genre;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.genre.*;
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
public class GenreService {
    public final GenreMapper genreMapper;
    public final GenreRepository genreRepository;


    public Integer create(GenreRequest request) {
        Genre genre = genreMapper.toGenre(request);
        return genreRepository.save(genre).getId();
    }

    public Integer update(Integer id, GenreRequest request) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found genre"));
        return genreRepository.save(genreMapper.setGenre(genre, request)).getId();
    }

    public void delete(Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found genre rate"));
         genreRepository.delete(genre);
    }

    public GenreResponse findById(Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found genre rate"));
        return genreMapper.toResponse(genre);

    }

    public PageResponse<GenreResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Genre> genre = genreRepository.findAll(pageable);
        List<GenreResponse> responses = genre.stream()
                .map(genreMapper::toResponse)
                .toList();
        return new PageResponse<>(
                responses,
                genre.getNumber(),
                genre.getSize(),
                genre.getTotalElements(),
                genre.getTotalPages(),
                genre.isFirst(),
                genre.isLast()
        );
    }


    public List<GenreResponse> findGenreAll() {
        List<GenreResponse> genreResponses = genreRepository.findAll()
                .stream()
                .map(genreMapper::toResponse)
                .toList();
        return genreResponses;

    }
}
