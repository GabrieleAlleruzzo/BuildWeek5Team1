package entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.User;

@Entity
public class Indirizzo {
    @Id @GeneratedValue
    private Long id;

    private String via;
    private String civico;
    private String localita;
    private String cap;

    @ManyToOne
    private Comune comune;

    public Long getId() {
    }

    public String getCivico() {
    }

    public String getLocalita() {
    }

    public String getVia() {
    }

    public String getCap() {
    }

    public Indirizzo getComune() {
    }

    public String getNome() {
    }

    public Indirizzo getProvincia() {
    }

    public String getSigla() {
    }
}