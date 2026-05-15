package ee.kontrolltoo.backend.repository;

import ee.kontrolltoo.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
