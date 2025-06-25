package epicode.BW5T1.repository;

import epicode.BW5T1.model.Comune;
import epicode.BW5T1.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComuneRepository extends JpaRepository<Comune, Long> {
    Optional<Comune> findByNomeAndProvincia(String nome, Provincia provincia);
}
