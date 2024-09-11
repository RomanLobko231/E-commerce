package com.micro.ecommerce.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"
)
public interface CustomerClient {

    @GetMapping("/{id}")
    Optional<CustomerInfoResponse> findCustomerById(@PathVariable("id") String id);

    @GetMapping("/exists/{id}")
    boolean existsById(@PathVariable("id") String id);
}
