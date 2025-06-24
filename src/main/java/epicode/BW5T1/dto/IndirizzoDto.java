package epicode.BW5T1.dto;
import lombok.Data;

@Data
public class IndirizzoDto {
    private Long id;
    private String via;
    private String civico;
    private String localita;
    private String cap;

    private Long idComune;
    private String nomeComune;
    private String nomeProvincia;
    private String siglaProvincia;
}
