package epicode.BW5T1.dto;

import epicode.BW5T1.enumeration.StatoFattura;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FatturaDto {
    private LocalDate data;
    private double importo;
    private String numero;
    private StatoFattura statoFattura;
}
