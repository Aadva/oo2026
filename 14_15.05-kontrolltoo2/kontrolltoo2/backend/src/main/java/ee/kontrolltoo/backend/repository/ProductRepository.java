package ee.kontrolltoo.backend.repository;

import ee.kontrolltoo.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
