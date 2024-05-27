package nextDevs.progettoSettimanale.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Dipendente {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String nome;
    private String cognome;
    private String email;

    private String avatar;

    @OneToMany(mappedBy="dipendente")
    private List<Dispositivo> dispositivi;

}
