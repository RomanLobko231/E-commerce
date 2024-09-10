package com.micro.ecommerce.order;

import com.micro.ecommerce.customer.CustomerClient;
import com.micro.ecommerce.exceptions.CustomerNotFoundException;
import com.micro.ecommerce.orderline.OrderLine;
import com.micro.ecommerce.orderline.OrderLineRequest;
import com.micro.ecommerce.orderline.OrderLineService;
import com.micro.ecommerce.product.ProductClient;
import com.micro.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderLineService orderLineService;


    public Integer createOrder(OrderRequest request) {
        if (!customerClient.existsById(request.customerId())) {
            throw new CustomerNotFoundException("Customer with id %s not found"
                    .formatted(request.customerId()));
        }

        productClient.purchaseProducts(request.purchaseRequests());

        Order savedOrder = orderRepository.save(orderMapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.purchaseRequests()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            savedOrder.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }



        return null;
    }
}
