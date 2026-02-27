package ee.mirko.veebipood.repository;

import ee.mirko.veebipood.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
