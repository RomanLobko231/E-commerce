package com.micro.ecommerce.product;

import com.micro.ecommerce.exceptions.ProductPurchaseException;
import com.micro.ecommerce.product.requests.ProductPurchaseRequest;
import com.micro.ecommerce.product.requests.ProductRequest;
import com.micro.ecommerce.product.responses.ProductPurchaseResponse;
import com.micro.ecommerce.product.responses.ProductResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public Integer create(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequests) {
        List<Integer> requestedProductIds = productPurchaseRequests
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        List<Product> requestedProductsInStock = productRepository.findAllById(requestedProductIds);
        if (requestedProductIds.size() != requestedProductsInStock.size()) {
            throw new ProductPurchaseException("One or more requested products were not found");
        }

        Map<Integer, Product> requestedProductsMap = requestedProductsInStock
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<ProductPurchaseResponse> purchaseResponseList = processPurchaseRequests(productPurchaseRequests, requestedProductsMap);
        productRepository.saveAll(requestedProductsInStock);

        return purchaseResponseList;
    }

    public ProductResponse findById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product with id %s was not found".formatted(id)));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    private List<ProductPurchaseResponse> processPurchaseRequests(
            List<ProductPurchaseRequest> productPurchaseRequests,
            Map<Integer, Product> requestedProductsMap
    ) {
        List<ProductPurchaseResponse> successfullyPurchasedProducts = new ArrayList<>();

        for (ProductPurchaseRequest productPurchaseRequest : productPurchaseRequests) {
            Product product = requestedProductsMap.get(productPurchaseRequest.productId());
            if (product == null) throw new ProductPurchaseException("Product with id %s not found".formatted(productPurchaseRequest.productId()));

            if (product.getAvailableQuantity() < productPurchaseRequest.requestedQuantity()) {
                throw new ProductPurchaseException(
                        "Only %s available while requested %s for product %s"
                                .formatted(
                                        product.getAvailableQuantity(),
                                        productPurchaseRequest.requestedQuantity(),
                                        product.getName())
                );
            }

            double newAvailableQuantity = product.getAvailableQuantity() - productPurchaseRequest.requestedQuantity();
            product.setAvailableQuantity(newAvailableQuantity);
            successfullyPurchasedProducts.add(productMapper.toProductPurchaseResponse(product, productPurchaseRequest.requestedQuantity()));
        }

        return successfullyPurchasedProducts;
    }

}
