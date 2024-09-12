package com.micro.ecommerce.orderline;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest request) {
            OrderLine orderLine = orderLineMapper.toOrderLine(request);
            return orderLineRepository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer id){
        return orderLineRepository
                .findAllByOrderId(id)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }

    public OrderLineResponse findById(Integer id) {
        return orderLineRepository
                .findById(id)
                .map(orderLineMapper::toOrderLineResponse)
                .orElseThrow(() -> new EntityNotFoundException("Order line with id %s was not found".formatted(id)));
    }
}
