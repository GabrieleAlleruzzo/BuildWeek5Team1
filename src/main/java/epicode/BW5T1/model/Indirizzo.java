package epicode.BW5T1.model;


import epicode.BW5T1.enumeration.TipoIndirizzo;
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
    private String cap;
    @Enumerated(EnumType.STRING)
    private TipoIndirizzo tipoIndirizzo;

    @ManyToOne
    @JoinColumn(name = "idComune", nullable = false)
    private Comune comune;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;


}