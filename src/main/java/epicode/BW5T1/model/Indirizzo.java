package epicode.BW5T1.model;


import model.Comune;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.User;
@Data
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


}