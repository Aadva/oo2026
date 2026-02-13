package ee.mirko.film_kodunetoo.controller;

import ee.mirko.film_kodunetoo.entity.Film;
import ee.mirko.film_kodunetoo.repository.FilmideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
public class FilmController {


    @Autowired
    private FilmideRepository filmideRepository;

    @GetMapping("filmid")
    public List<Film> getFilms() {
        return filmideRepository.findAll();
    }

    @DeleteMapping("filmid/{id}")
    public List<Film> deleteFilm(@PathVariable Long id){
        filmideRepository.deleteById(id);
        return filmideRepository.findAll();
    }
    @PostMapping("filmid")
    public List<Film> addFilm(@RequestBody Film film){
        filmideRepository.save(film);
        return filmideRepository.findAll();
    }



}