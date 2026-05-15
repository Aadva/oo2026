package ee.kontrolltoo.backend.service;

import ee.kontrolltoo.backend.entity.OrderRow;
import ee.kontrolltoo.backend.entity.Product;
import ee.kontrolltoo.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Double calculateOrderSum(List<OrderRow> orderRows) {
        double total = 0;
        for (OrderRow orderRow : orderRows) {
            Product dbProduct = productRepository.findById(orderRow.getId()).orElseThrow();
            total += dbProduct.getPrice() * orderRow.getQuantity();
        }
        return total;
    }
}
