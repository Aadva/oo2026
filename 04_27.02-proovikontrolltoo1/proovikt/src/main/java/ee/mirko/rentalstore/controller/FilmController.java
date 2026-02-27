package ee.mirko.rentalstore.controller;

import ee.mirko.rentalstore.entity.Film;
import ee.mirko.rentalstore.repository.FilmideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class FilmController {

    private static final double PREMIUM_PRICE = 4.0;
    private static final double BASIC_PRICE = 3.0;


    @Autowired
    private FilmideRepository filmideRepository;

    @GetMapping
    public List<Film> getFilms() {
        return filmideRepository.findAll();
    }

    @GetMapping("/in-store")
    public List<Film> getFilmsInStore() {
        return filmideRepository.findByRentedFalse();
    }

    @DeleteMapping("/{id}")
    public List<Film> deleteFilm(@PathVariable Long id) {
        if (!filmideRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Filmi ei leitud");
        }
        filmideRepository.deleteById(id);
        return filmideRepository.findAll();
    }

    @PostMapping
    public List<Film> addFilm(@RequestBody Film film) {
        film.setRented(false);
        film.setDueDate(null);
        filmideRepository.save(film);
        return filmideRepository.findAll();
    }

    @PatchMapping("/{id}/type")
    public Film changeFilmType(@PathVariable Long id, @RequestBody ChangeTypeRequest request) {
        Film film = getFilmOrThrow(id);
        film.setType(request.type());
        return filmideRepository.save(film);
    }

    @PostMapping("/{id}/rent")
    public RentResponse rentFilm(@PathVariable Long id, @RequestBody RentRequest request) {
        Film film = getFilmOrThrow(id);
        if (film.isRented()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Film on juba välja renditud");
        }

        if (request.days() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rendipäevade arv peab olema > 0");
        }

        LocalDate dueDate = LocalDate.now().plusDays(request.days());
        double rentalCost = calculateRentalPrice(film.getType(), request.days());

        film.setRented(true);
        film.setDueDate(dueDate);
        filmideRepository.save(film);

        return new RentResponse(film.getId(), film.getTitle(), request.days(), rentalCost, dueDate);
    }

    @PostMapping("/{id}/return")
    public ReturnResponse returnFilm(@PathVariable Long id) {
        Film film = getFilmOrThrow(id);
        if (!film.isRented()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Film ei ole hetkel renditud");
        }

        LocalDate today = LocalDate.now();
        LocalDate dueDate = film.getDueDate();
        long lateDays = 0;
        if (dueDate != null && today.isAfter(dueDate)) {
            lateDays = ChronoUnit.DAYS.between(dueDate, today);
        }

        double lateFee = calculateLateFee(film.getType(), lateDays);

        film.setRented(false);
        film.setDueDate(null);
        filmideRepository.save(film);

        return new ReturnResponse(film.getId(), film.getTitle(), lateDays, lateFee);
    }

    private Film getFilmOrThrow(Long id) {
        return filmideRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filmi ei leitud"));
    }

    private double calculateRentalPrice(String filmType, int days) {
        String normalizedType = normalizeType(filmType);

        return switch (normalizedType) {
            case "NEW_RELEASE" -> PREMIUM_PRICE * days;
            case "REGULAR" -> BASIC_PRICE + (BASIC_PRICE * Math.max(0, days - 3));
            case "OLD", "OLD_FILM" -> BASIC_PRICE + (BASIC_PRICE * Math.max(0, days - 5));
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Tundmatu filmi tüüp. Kasuta: NEW_RELEASE, REGULAR, OLD");
        };
    }

    private double calculateLateFee(String filmType, long lateDays) {
        if (lateDays <= 0) {
            return 0;
        }

        String normalizedType = normalizeType(filmType);

        return switch (normalizedType) {
            case "NEW_RELEASE" -> PREMIUM_PRICE * lateDays;
            case "REGULAR", "OLD", "OLD_FILM" -> BASIC_PRICE * lateDays;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Tundmatu filmi tüüp. Kasuta: NEW_RELEASE, REGULAR, OLD");
        };
    }

    private String normalizeType(String filmType) {
        if (filmType == null || filmType.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Filmi tüüp puudub. Kasuta: NEW_RELEASE, REGULAR, OLD");
        }

        return filmType.trim().replace('-', '_').replace(' ', '_').toUpperCase();
    }

    public record ChangeTypeRequest(String type) {
    }

    public record RentRequest(int days) {
    }

    public record RentResponse(Long filmId, String title, int days, double rentalCost, LocalDate dueDate) {
    }

    public record ReturnResponse(Long filmId, String title, long lateDays, double lateFee) {
    }
}
