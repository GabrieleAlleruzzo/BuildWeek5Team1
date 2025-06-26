package epicode.BW5T1.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotEmpty(message = "L'username non può essere vuoto")
    private String username;
    @NotEmpty(message = "L'username non può essere vuoto")
    private String email;
    @NotEmpty(message = "L'username non può essere vuoto")
    private String password;

}
