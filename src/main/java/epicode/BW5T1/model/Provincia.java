package epicode.BW5T1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Entity
@Data
@ToString(exclude = "comuni")
public class Provincia {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String sigla;

    @JsonIgnore
    @OneToMany(mappedBy = "provincia")
    private Set<Comune> comuni ;

}
