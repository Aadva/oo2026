package ee.mirko.rentalstore.repository;

import ee.mirko.rentalstore.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmideRepository extends JpaRepository<Film, Long> {
    List<Film> findByRentedFalse();
}
