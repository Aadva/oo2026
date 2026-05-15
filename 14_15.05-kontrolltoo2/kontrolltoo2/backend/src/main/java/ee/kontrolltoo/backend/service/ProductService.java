package ee.kontrolltoo.backend.service;

import ee.kontrolltoo.backend.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public void validate(Product product) {
        if (product.getTitle() == null || product.getTitle().isBlank()) {
            throw new RuntimeException("Product title is required.");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new RuntimeException("Product price is wrong.");
        }
        if (product.getImage() == null || product.getImage().isBlank()) {
            throw new RuntimeException("Product image is required.");
        }
    }
}
