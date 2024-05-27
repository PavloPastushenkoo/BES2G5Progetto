package nextDevs.progettoSettimanale.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DipendenteDto {

    @NotBlank(message = "L'username non può essere vuoto")
    @Size( max = 20)
    private String username;
    @NotBlank(message = "Il nome non può essere vuoto")
    @Size( max = 20)
    private String nome;
    @NotBlank(message = "Il cognome non può essere vuoto")
    @Size( max = 20)
    private String cognome;

    @NotBlank(message = "L'email non può essere vuota")
    @Email ( regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    @Size( max = 20)
    private String email;

}
