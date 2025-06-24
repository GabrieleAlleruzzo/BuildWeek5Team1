package epicode.BW5T1.dto;

import epicode.BW5T1.enumeration.TipoCliente;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClienteDto {
    @NotEmpty(message = "La ragione sociale non può essere vuota")
    private String ragioneSociale;

    @NotEmpty(message = "La partita IVA non può essere vuota")
    private String partitaIva;

    @Email(message = "Email non valida")
    @NotEmpty(message = "L'email non può essere vuota")
    private String email;

    @NotNull(message = "La data di inserimento è obbligatoria")
    private LocalDate dataInserimento;

    @NotNull(message = "La data dell'ultimo contatto è obbligatoria")
    private LocalDate dataUltimoContatto;

    @NotNull(message = "Il fatturato è obbligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "Il fatturato deve essere positivo")
    private BigDecimal fatturatoAnnuale;

    @NotEmpty(message = "La PEC non può essere vuota")
    private String pec;

    @NotEmpty(message = "Il telefono non può essere vuoto")
    private String telefono;

    @NotEmpty(message = "L'email del contatto non può essere vuota")
    private String emailContatto;

    @NotEmpty(message = "Il nome del contatto non può essere vuoto")
    private String nomeContatto;

    @NotEmpty(message = "Il cognome del contatto non può essere vuoto")
    private String cognomeContatto;

    @NotEmpty(message = "Il telefono del contatto non può essere vuoto")
    private String telefonoContatto;

    private String logoAziendale;

    @NotNull(message = "Il tipo cliente è obbligatorio")
    private TipoCliente tipoCliente;

}
