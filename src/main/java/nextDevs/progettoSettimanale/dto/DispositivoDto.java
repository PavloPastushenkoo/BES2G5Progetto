package nextDevs.progettoSettimanale.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nextDevs.progettoSettimanale.enums.StatoDispositivo;

@Data
public class DispositivoDto {

    @NotBlank(message = "Il nome non può essere vuoto")
    @Size( max = 20)
    private String nome;
    @NotBlank(message = "La marca non può essere vuoto")
    @Size( max = 20)
    private String marca;
    @NotNull(message= "Lo stato non può essere vuoto")
    private StatoDispositivo stato;


    private int dipendenteId;
    @NotNull
    private String tipoDispositivo;
}
