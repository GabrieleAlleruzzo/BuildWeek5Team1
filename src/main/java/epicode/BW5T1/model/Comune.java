package epicode.BW5T1.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Comune {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "comune_id", nullable = false)
    private Provincia provincia;
}
