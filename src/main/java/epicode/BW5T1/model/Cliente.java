package epicode.BW5T1.model;

import epicode.BW5T1.enumeration.TipoCliente;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table(name = "clienti")
@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue
    private int id;

    private String ragioneSociale;
    private String partitaIva;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private BigDecimal fatturatoAnnuale;
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente; // PA, SAS, SPA, SRL

    @OneToMany(mappedBy = "cliente")
    private Set<Indirizzo> indirizzi ;

    @OneToMany(mappedBy = "cliente")
    private Set<Fattura> fatture ;

}
