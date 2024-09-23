package com.micro.ecommerce.product;

import com.micro.ecommerce.product.requests.ProductPurchaseRequest;
import com.micro.ecommerce.product.requests.ProductRequest;
import com.micro.ecommerce.product.responses.ProductPurchaseResponse;
import com.micro.ecommerce.product.responses.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product Microservice", description = "Microservice that directly works with products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Adds a new product")
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(productService.create(request));
    }

    @PostMapping("/purchase")
    @Operation(summary = "Processes purchased products")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<ProductPurchaseRequest> requestList){
        return ResponseEntity.ok(productService.processPurchasedProducts(requestList));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds a product given its Id")
    public ResponseEntity<ProductResponse> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Finds all products")
    public ResponseEntity<List<ProductResponse>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
}
