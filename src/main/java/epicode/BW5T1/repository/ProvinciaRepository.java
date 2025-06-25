package epicode.BW5T1.repository;

import epicode.BW5T1.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    Optional<Provincia> findBySigla(String sigla);
   List<Provincia> findByNomeIgnoreCase(String nome);

    List<Provincia> findBySiglaAndNomeIgnoreCase(String sigla, String nome);
//Nuovo
    //Optional<Provincia> findByNomeIgnoreCase(String nome);

    boolean existsByProvincia(Long id);
}
