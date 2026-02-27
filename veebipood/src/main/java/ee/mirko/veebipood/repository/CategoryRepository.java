package ee.mirko.veebipood.repository;

import ee.mirko.veebipood.entity.Category;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<@NonNull Category,@NonNull Long> {
}
