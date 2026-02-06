package ee.mirko.veebipood.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean active;
    private int stock;
    // Panen andmebaasi, aga ei määra seda väärtust:
    // double -> 0
    // boolean -> false
    // int -> 0

    // Panen andmebaasi, aga ei määra seda väärtust:
    // Double -> null
    // Boolean -> null
    // Integer -> null
}
