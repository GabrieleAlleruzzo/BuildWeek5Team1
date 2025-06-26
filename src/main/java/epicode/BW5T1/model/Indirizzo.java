package epicode.BW5T1.model;


import jakarta.persistence.*;
import lombok.Data;
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
    @JoinColumn(name = "indirizzo_id_comune", nullable = false)
    private Comune comune;

    @ManyToOne
    @JoinColumn(name = "indirizzo_id_cliente", nullable = false)
    private Cliente cliente;


}