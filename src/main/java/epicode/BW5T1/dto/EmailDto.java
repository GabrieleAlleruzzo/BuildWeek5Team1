package epicode.BW5T1.dto;


import lombok.Data;

@Data
public class EmailDto {
    private String mittente;
    private String destinatario;
    private String messaggio;

}
