package com.micro.ecommerce.order;

import com.micro.ecommerce.customer.CustomerClient;
import com.micro.ecommerce.customer.CustomerInfoResponse;
import com.micro.ecommerce.exceptions.CustomerNotFoundException;
import com.micro.ecommerce.exceptions.OrderNotFoundException;
import com.micro.ecommerce.kafka.OrderConfirmation;
import com.micro.ecommerce.kafka.OrderProducer;
import com.micro.ecommerce.orderline.OrderLineRequest;
import com.micro.ecommerce.orderline.OrderLineService;
import com.micro.ecommerce.product.ProductClient;
import com.micro.ecommerce.product.PurchaseRequest;
import com.micro.ecommerce.product.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;


    public Integer createOrder(OrderRequest request) {
        CustomerInfoResponse customer = customerClient
                .findCustomerById(request.customerId())
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer with id %s not found".formatted(request.customerId()))
                );

        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(request.purchaseRequests());

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

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return savedOrder.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return orderRepository
                .findById(id)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new OrderNotFoundException("Order with id %s was not found".formatted(id)));
    }

    public void deleteById(Integer id) {
        orderRepository.deleteById(id);
    }
}
