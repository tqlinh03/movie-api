package com.tqlinh.movie.modal.payment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Value("${application.payment.vnpay.payUrl}")
    private String vnp_PayUrl;

    @Value("${application.payment.vnpay.returnUrl}")
    private String vnp_ReturnUrl;

    @Value("${application.payment.vnpay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${application.payment.vnpay.secretKey}")
    private String secretKey;

    @Value("${application.payment.vnpay.apiUrl}")
    private String vnp_ApiUrl;

    @Value("${application.payment.vnpay.vnp_Version}")
    private String vnp_Version;

    @Transactional
    public Payment create(PaymentRequest paymentRequest, HttpServletRequest request) throws UnsupportedEncodingException {
        String vnp_Command = "pay";
        String orderType = "other";
        String bankCode = "NCB";
        long vnp_Amount = Long.parseLong(paymentRequest.amount().toString()) * 100;

        String vnp_TxnRef = vnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = vnPayConfig.getIpAddress(request);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnPayConfig.hmacSHA512(secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnp_PayUrl + "?" + queryUrl;

        Payment payment = paymentMapper.toPayment(paymentRequest);
        payment.setPaymentUrl(paymentUrl);
        return paymentRepository.save(payment);
    }


    public Integer processPayment(ProcessPaymentRequest request) {

        return  null;
    }

    public String paymentQuery(PaymentQueryRequest paymentQueryRequest, HttpServletRequest request) {
        String vnp_RequestId = vnPayConfig.getRandomNumber(8);
        String vnp_Command = "querydr";
        String vnp_OrderInfo = "Kiem tra ket qua GD OrderId:" + paymentQueryRequest.vnp_TxnRef();

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        String vnp_IpAddr = vnPayConfig.getIpAddress(request);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_RequestId", vnp_RequestId);
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", paymentQueryRequest.vnp_TxnRef());
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_TransactionDate", paymentQueryRequest.vnp_TransactionDate());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);


        String hash_Data= String.join("|", vnp_RequestId, vnp_Version, vnp_Command,
                vnp_TmnCode, paymentQueryRequest.vnp_TxnRef(), paymentQueryRequest.vnp_TransactionDate(),
                vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);
        String vnp_SecureHash = vnPayConfig.hmacSHA512(vnPayConfig.secretKey, hash_Data.toString());

        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

        String apiUrl = vnp_ApiUrl;
        String response = sendPostRequest(apiUrl, vnp_Params.toString());

        return null;
    }

    public String sendPostRequest(String apiUrl, String postData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(postData, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}
