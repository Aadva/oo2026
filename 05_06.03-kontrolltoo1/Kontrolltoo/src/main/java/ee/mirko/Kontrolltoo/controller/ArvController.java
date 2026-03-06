package ee.mirko.Kontrolltoo.controller;

import ee.mirko.Kontrolltoo.entity.Arv;
import ee.mirko.Kontrolltoo.repository.ArvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/arvud")
public class ArvController {

    @Autowired
    private ArvRepository arvRepository;

    @PostMapping
    public ResponseEntity<?> lisaArv(@RequestBody Map<String, Integer> payload) {
        if (payload == null) {
            return ResponseEntity.badRequest().body(Map.of("viga", "Päringu keha on puudu."));
        }

        Integer arv = payload.get("arv");

        if (arv == null) {
            return ResponseEntity.badRequest().body(Map.of("viga", "Väli 'arv' on kohustuslik."));
        }

        if (arv < 0) {
            return ResponseEntity.badRequest().body(Map.of("viga", "Arv ei tohi olla negatiivne."));
        }

        if (arv > 1000000) {
            return ResponseEntity.badRequest().body(Map.of("viga", "Arv ei tohi olla suurem kui 1 000 000."));
        }

        Arv uusArv = new Arv(arv);
        Arv salvestatud = arvRepository.save(uusArv);

        Map<String, Object> vastus = new HashMap<>();
        vastus.put("teade", "Arv lisatud edukalt.");
        vastus.put("andmed", salvestatud);

        return ResponseEntity.status(HttpStatus.CREATED).body(vastus);
    }

    @GetMapping
    public List<Arv> koikArvud() {
        return arvRepository.findAll();
    }

    @GetMapping("/teisenda")
    public ResponseEntity<?> teisendaArvud(@RequestParam("formaat") String formaat) {
        String normaliseeritudFormaat = formaat.toLowerCase(Locale.ROOT).trim();

        if (!normaliseeritudFormaat.equals("binaar")
                && !normaliseeritudFormaat.equals("kaheksand")
                && !normaliseeritudFormaat.equals("kuueteist")) {
            return ResponseEntity.badRequest().body(
                    Map.of("viga", "Lubatud formaadid on: binaar, kaheksand, kuueteist."));
        }

        List<Map<String, Object>> teisendused = arvRepository.findAll().stream()
                .map(arvObjekt -> {
                    Map<String, Object> rida = new HashMap<>();
                    rida.put("id", arvObjekt.getId());
                    rida.put("algneArv", arvObjekt.getArv());
                    rida.put("formaat", normaliseeritudFormaat);
                    rida.put("teisendatud", teisendaArv(arvObjekt.getArv(), normaliseeritudFormaat));
                    return rida;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(teisendused);
    }

    private String teisendaArv(int arv, String formaat) {
        return switch (formaat) {
            case "binaar" -> Integer.toBinaryString(arv);
            case "kaheksand" -> Integer.toOctalString(arv);
            case "kuueteist" -> Integer.toHexString(arv).toUpperCase(Locale.ROOT);
            default -> throw new IllegalArgumentException("Tundmatu formaat: " + formaat);
        };
    }
}
