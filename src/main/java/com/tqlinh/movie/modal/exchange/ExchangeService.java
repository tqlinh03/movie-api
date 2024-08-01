package com.tqlinh.movie.modal.exchange;

import com.tqlinh.movie.modal.payment.Payment;
import com.tqlinh.movie.modal.payment.PaymentRequest;
import com.tqlinh.movie.modal.payment.PaymentService;
import com.tqlinh.movie.modal.point.Point;
import com.tqlinh.movie.modal.point.PointRepository;
import com.tqlinh.movie.modal.user.User;
import com.tqlinh.movie.modal.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    public final ExchangeMapper exchangeMapper;
    public final PaymentService paymentService;
    public final ExchangeRepository exchangeRepository;
    public final PointRepository pointRepository;

    @Transactional
    public Integer create(ExchangeRequest request, Authentication connectedUser, HttpServletRequest req) throws UnsupportedEncodingException {
        PaymentRequest paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod()
        );
        Payment payment = paymentService.create(paymentRequest, req);
        User user = ((User) connectedUser.getPrincipal());
        Point point = pointRepository.findById(user.getPoint().getId())
                .orElseThrow(() -> new RuntimeException("Point not found"));

        Exchange exchange = exchangeMapper.toExchange(request);
        exchange.setPayment(payment);
        exchange.setPoint(point);
        return exchangeRepository.save(exchange).getId();
    }

    public ExchangeResponse findById(Integer id) {
        Exchange exchange = exchangeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange not found"));
        return exchangeMapper.toResponse(exchange);
    }
}
