package com.tqlinh.movie.modal.exchangeRate;

import com.tqlinh.movie.common.PageResponse;
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
public class ExchangeRateService {
    public final ExchangeRateMapper exchangeRateMapper;
    public final  ExchangeRateRepository exchangeRateRepository;


    public Integer create(ExchangeRateRequest request) {
        ExchangeRate exchangeRate = exchangeRateMapper.toExchangeRate(request);
        return exchangeRateRepository.save(exchangeRate).getId();
    }

    public Integer update(Integer id, ExchangeRateRequest request) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found exchange rate"));
        return exchangeRateRepository.save(exchangeRateMapper.setExchangeRate(exchangeRate, request)).getId();
    }

    public void delete(Integer id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found exchange rate"));
         exchangeRateRepository.delete(exchangeRate);
    }

    public ExchangeRateResponse findById(Integer id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found exchange rate"));
        return exchangeRateMapper.toExchangeRateResponse(exchangeRate);

    }

    public PageResponse<ExchangeRateResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ExchangeRate> exchangeRates = exchangeRateRepository.findAll(pageable);
        List<ExchangeRateResponse> responses = exchangeRates.stream()
                .map(exchangeRateMapper::toExchangeRateResponse)
                .toList();
        return new PageResponse<>(
                responses,
                exchangeRates.getNumber(),
                exchangeRates.getSize(),
                exchangeRates.getTotalElements(),
                exchangeRates.getTotalPages(),
                exchangeRates.isFirst(),
                exchangeRates.isLast()
        );
    }

//    public List<ExchangeRateResponse> findAll() {
//        List<ExchangeRateResponse> exchangeRateResponsese = exchangeRateRepository.findAll()
//                .stream()
//                .map(exchangeRateMapper::toExchangeRateResponse)
//                .toList();
//        return exchangeRateResponsese;
//
//    }
}
