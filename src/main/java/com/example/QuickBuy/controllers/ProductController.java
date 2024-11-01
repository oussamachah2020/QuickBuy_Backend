package com.example.QuickBuy.controllers;

import com.example.QuickBuy.filters.TokenDecoder;
import com.example.QuickBuy.models.product.Product;
import com.example.QuickBuy.models.product.ProductDto;
import com.example.QuickBuy.models.product.ProductEditDto;
import com.example.QuickBuy.services.owner.AuthenticationService;
import com.example.QuickBuy.services.owner.JwtService;
import com.example.QuickBuy.services.product.ProductService;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final TokenDecoder tokenDecoder;
    private final JwtService jwtService;


    public ProductController(ProductService productService, TokenDecoder tokenDecoder, JwtService jwtService) {
        this.productService = productService;
        this.tokenDecoder = tokenDecoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto req, @RequestHeader("Authorization") String authHeader) {
        String token = tokenDecoder.getToken(authHeader);
        String ownerId = jwtService.extractUserId(token);

        Product product = productService.addProduct(req, ownerId);

        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductDetails(@PathVariable(name = "id") String id) {
        Optional<Product> productDetails = productService.getProductDetails(id);

        return ResponseEntity.ok(productDetails);
    }

    @PutMapping(path = "/edit/{productId}")
    public ResponseEntity<Object> updateProduct(@PathVariable String productId, @RequestBody ProductEditDto req) {
        Product updatedProduct = productService.updateProductById(productId, req);

        if (updatedProduct != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Product updated successfully");
            return ResponseEntity.status(200).body(response);
        } else {
            return ResponseEntity.status(404).body("Product does not exist");
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        String deleteResult = productService.removeProductById(productId);

        return ResponseEntity.ok(deleteResult);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();

        return ResponseEntity.ok(products);
    }
}
