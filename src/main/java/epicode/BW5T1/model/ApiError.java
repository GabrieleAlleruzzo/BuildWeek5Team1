package epicode.BW5T1.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String messaggio;
    private LocalDateTime dataErrore;
}
