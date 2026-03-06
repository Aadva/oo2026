package ee.mirko.Kontrolltoo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Arv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int arv;

    public Arv() {
    }

    public Arv(int arv) {
        this.arv = arv;
    }

    public Long getId() {
        return id;
    }

    public int getArv() {
        return arv;
    }

    public void setArv(int arv) {
        this.arv = arv;
    }
}
