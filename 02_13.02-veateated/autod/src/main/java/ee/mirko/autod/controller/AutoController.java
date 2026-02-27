package ee.mirko.autod.controller;

import ee.mirko.autod.entity.Auto;
import ee.mirko.autod.repository.AutodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("autod")
public class AutoController {

    private final AutodeRepository autodeRepository;

    public AutoController(AutodeRepository autodeRepository) {
        this.autodeRepository = autodeRepository;
    }

    @GetMapping
    public List<Auto> getAutod() {
        return autodeRepository.findAll();
    }

    @PostMapping
    public Auto lisaAuto(@RequestBody Auto auto) {
        valideeriAuto(auto);
        return autodeRepository.save(auto);
    }

    private void valideeriAuto(Auto auto) {
        if (auto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Auto andmed puuduvad.");
        }
        if (auto.getMark() == null || auto.getMark().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mark ei tohi olla tühi.");
        }
        if (auto.getModel() == null || auto.getModel().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mudel ei tohi olla tühi.");
        }
        int maxAllowedYear = Year.now().getValue() + 1;
        if (auto.getYear() == null || auto.getYear() < 1886 || auto.getYear() > maxAllowedYear) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Aasta peab olema vahemikus 1886 kuni " + maxAllowedYear + ".");
        }
        if (auto.getPrice() == null || auto.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hind peab olema suurem kui 0.");
        }
        if (auto.getMileage() == null || auto.getMileage() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Läbisõit ei tohi olla negatiivne.");
        }
        auto.setMark(auto.getMark().trim());
        auto.setModel(auto.getModel().trim());
    }
}
