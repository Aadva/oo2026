package ee.mirko.sportlased.repository;

import ee.mirko.sportlased.entity.Sportlane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportlaseRepository extends JpaRepository<Sportlane, Long> {
}