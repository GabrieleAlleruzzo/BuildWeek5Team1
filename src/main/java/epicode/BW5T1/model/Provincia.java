package epicode.BW5T1.model;


import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Provincia {
    @Id

    private String Sigla;
    private String Provincia;
    private String Regione;


    @OneToMany(mappedBy = "provincia")
    private List<Comune> comuni;
}
