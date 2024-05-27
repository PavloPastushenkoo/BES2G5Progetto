package nextDevs.progettoSettimanale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import nextDevs.progettoSettimanale.enums.StatoDispositivo;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Dispositivo {

    @Id
    @GeneratedValue
    private Integer id;
    private String nome;
    private String marca;
    @Enumerated(EnumType.STRING)
    private StatoDispositivo stato;

    private String tipoDispositivo;


    @ManyToOne
    @JoinColumn(name = "dipendente_username")
    @JsonIgnore
    private Dipendente dipendente;
}
