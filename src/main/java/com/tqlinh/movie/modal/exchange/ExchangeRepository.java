package com.tqlinh.movie.modal.exchange;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {
    Exchange findByOrderCode(long l);
}
