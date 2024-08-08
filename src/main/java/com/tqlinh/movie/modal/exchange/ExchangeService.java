package com.tqlinh.movie.modal.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tqlinh.movie.modal.exchangeRate.ExchangeRate;
import com.tqlinh.movie.modal.exchangeRate.ExchangeRateRepository;
import com.tqlinh.movie.modal.payment.Payment;
import com.tqlinh.movie.modal.payment.PaymentRequest;
import com.tqlinh.movie.modal.payment.PaymentService;
import com.tqlinh.movie.modal.point.Point;
import com.tqlinh.movie.modal.point.PointRepository;
import com.tqlinh.movie.modal.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.PaymentLinkData;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    public final ExchangeMapper exchangeMapper;
    public final PaymentService paymentService;
    public final ExchangeRepository exchangeRepository;
    public final PointRepository pointRepository;
    private final PayOS payOS;
    private final ExchangeRateRepository exchangeRateRepository;

    @Transactional
    public String createPaymentLink(CreatePaymentLinkRequest request,
                                    Authentication connectedUser
    ) throws Exception {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(request.getPrice())
                .paymentMethod(request.getPaymentMethod())
                .build();
        Payment payment = paymentService.save(paymentRequest);

        User user = ((User) connectedUser.getPrincipal());
        Point point = pointRepository.findById(user.getPoint().getId())
                .orElseThrow(() -> new RuntimeException("Point not found"));

        Long orderCode = System.currentTimeMillis() / 1000;
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeRateId(request.getExchangeRate_id())
                .payment(payment)
                .point(point)
                .orderCode(orderCode)
                .build();
        Exchange exchange = exchangeMapper.toExchange(exchangeRequest);
        exchangeRepository.save(exchange);

        request.setOrderCode(orderCode);
        String payment_URL = paymentService.createPaymentLink(request);
        return payment_URL;
    }

    public ObjectNode confirmWebhook(Map<String, Object> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            Map<String, Object> data = (Map<String, Object>) requestBody.get("data");
            Integer orderCode = (Integer)  data.get("orderCode");
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode.longValue());

            if(order != null && "PAID".equals(order.getStatus())) {
                Exchange exchange = exchangeRepository.findByOrderCode(orderCode.longValue());
                ExchangeRate exchangeRate = exchangeRateRepository.findById(exchange.getExchangeRate().getId())
                        .orElseThrow(() -> new RuntimeException("ExchangeRate not found"));
                Point point = pointRepository.findById(exchange.getPoint().getId())
                        .orElseThrow(() -> new RuntimeException("Point not found"));

                Integer amount = exchange.getPayment().getAmount().intValue();
                Integer pointRate = exchangeRate.getPoint();
                Integer currency = exchangeRate.getCurrency();
                Integer plusPoint = (amount * currency)/ pointRate;
                point.setPoint(point.getPoint() + plusPoint);
                pointRepository.save(point);
            }


            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }
}
