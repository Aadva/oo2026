package ee.mirko.autod.repository;

import ee.mirko.autod.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutodeRepository extends JpaRepository<Auto, Long> {
    boolean existsByVinIgnoreCase(String vin);
}
