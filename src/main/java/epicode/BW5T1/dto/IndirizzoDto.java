package epicode.BW5T1.dto;

import epicode.BW5T1.enumeration.TipoIndirizzo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IndirizzoDto {

    @NotEmpty           // ← via non può essere vuota
    private String via;

    @NotEmpty
    private String civico;

    @NotEmpty
    private String cap;

    @NotNull(message = "Specificare SEDE_LEGALE o SEDE_OPERATIVA")
    private TipoIndirizzo tipoIndirizzo;

    @NotNull(message = "idComune obbligatorio")
    private Long idComune;

    @NotNull(message = "idCliente obbligatorio")
    private Long idCliente;
}

