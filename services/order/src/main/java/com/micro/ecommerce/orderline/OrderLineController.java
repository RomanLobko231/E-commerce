package com.micro.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-line")
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderLineService orderLineService;

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderLineResponse>> findAllByOrderId(@PathVariable Integer id) {
        return ResponseEntity.ok(orderLineService.findAllByOrderId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderLineResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderLineService.findById(id));
    }
}
