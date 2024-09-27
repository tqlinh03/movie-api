package com.tqlinh.movie.modal.exchangeRate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    Optional<ExchangeRate> findTopByOrderByCreatedDateDesc();
}
