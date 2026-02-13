package ee.mirko.film_kodunetoo.repository;

import ee.mirko.film_kodunetoo.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

//repository -> andmebaasiga suhtlemiseks. Selle sees on funktsioonid, mida on võimalik andmebaasiga teha.

public interface FilmideRepository extends JpaRepository <Film, Long > {
    Long id(Long id);
}
