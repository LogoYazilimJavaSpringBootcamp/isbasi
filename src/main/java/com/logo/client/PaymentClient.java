package com.logo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.logo.dto.Payment;

@FeignClient(value = "payment-service", url = "${payment.url}")
public interface PaymentClient {

	@PostMapping(value = "/payments")
	Payment createPayment(@RequestBody Payment payment);

}
