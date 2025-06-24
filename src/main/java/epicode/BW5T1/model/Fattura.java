package epicode.BW5T1.model;

import epicode.BW5T1.enumeration.StatoFattura;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
@Table(name = "fatture")

public class Fattura {
    @Id
    @GeneratedValue
    private int id;
    private LocalDate data;
    private double importo; 
    private String numero;

    @Enumerated
    private StatoFattura statoFattura;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;


}
