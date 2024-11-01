package com.example.QuickBuy.services.product;

import com.example.QuickBuy.models.product.Product;
import com.example.QuickBuy.models.product.ProductDto;
import com.example.QuickBuy.models.product.ProductEditDto;
import com.example.QuickBuy.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product addProduct(ProductDto product, String ownerId) {
        Product newProduct = new Product();

        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setAvailableQuantity(product.getAvailableQuantity());
        newProduct.setUnitPrice(product.getUnitPrice());
        newProduct.setAvailable(product.getAvailable());
        newProduct.setOwnerId(ownerId);

        return repository.save(newProduct);
    }

    public Optional<Product> getProductDetails(String id) {
        return repository.findById(id);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product updateProductById(String productId, ProductEditDto req) {

        Product existingProduct = repository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        existingProduct.setName(req.getName());
        existingProduct.setDescription(req.getDescription());
        existingProduct.setAvailableQuantity(req.getAvailableQuantity());
        existingProduct.setUnitPrice(req.getUnitPrice());
        existingProduct.setAvailable(req.getAvailable());

        return repository.save(existingProduct);
    }

    public String removeProductById(String productId) {
        try {
            repository.deleteById(productId);
            return "Product deleted successfully";
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());  // Outputs: Product with ID someProductId does not exist.
        }
        return productId;
    }

}
