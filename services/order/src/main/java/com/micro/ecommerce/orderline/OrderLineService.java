package com.micro.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest request) {
            OrderLine orderLine = orderLineMapper.toOrderLine(request);
            return orderLineRepository.save(orderLine).getId();
    }
}
