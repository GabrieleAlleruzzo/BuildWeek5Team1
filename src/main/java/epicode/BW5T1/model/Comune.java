package epicode.BW5T1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Comune {
    @Id
    private String id;
    private String nome;

    @ManyToOne
    private Provincia provincia;


}
