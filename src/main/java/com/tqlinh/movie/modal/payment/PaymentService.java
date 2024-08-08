package com.tqlinh.movie.modal.payment;

import com.tqlinh.movie.modal.exchange.CreatePaymentLinkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PayOS payOS;

    @Value("${application.payment.frontend.DOMAIN_FRONTEND_PAYMENT_SUCCESS}")
    private String DOMAIN_FRONTEND_PAYMENT_SUCCESS;

    @Value("${application.payment.frontend.DOMAIN_FRONTEND_PAYMENT_CANCEL}")
    private String DOMAIN_FRONTEND_PAYMENT_CANCEL;

    @Transactional
    public String createPaymentLink(CreatePaymentLinkRequest request) throws Exception {
        ItemData itemData = ItemData
                .builder()
                .name("Đổi điểm")
                .quantity(1)
                .price((request.getPrice()).intValue())
                .build();

        PaymentData paymentData = PaymentData
                .builder()
                .orderCode(request.getOrderCode())
                .amount((request.getPrice()).intValue())
                .description("Thanh toán đơn hàng")
                .returnUrl(DOMAIN_FRONTEND_PAYMENT_SUCCESS)
                .cancelUrl(DOMAIN_FRONTEND_PAYMENT_CANCEL)
                .item(itemData)
                .build();

        CheckoutResponseData result = payOS.createPaymentLink(paymentData);
        return result.getCheckoutUrl();
    }

    public Payment save(PaymentRequest paymentRequest) {
        return paymentRepository.save(paymentMapper.toPayment(paymentRequest));
    }
}
