package epicode.BW5T1.model;

import epicode.BW5T1.enumeration.StatoFattura;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Fattura {
    @Id
    @GeneratedValue
    private int id;
    private LocalDate data;
    private String importo; // BigDecimal sarebbe più adatto: il numero non viene visto come stringa.
    private String numero;

    @Enumerated
    private StatoFattura statoFattura;



}
