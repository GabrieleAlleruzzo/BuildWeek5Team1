package epicode.BW5T1.repository;

import epicode.BW5T1.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

    Optional<Provincia> findBySigla(String sigla);

    // ✅ Metodo sistemato: restituisce Optional, non List
    Optional<Provincia> findByNomeIgnoreCase(String nome);

    // Se ti serve proprio questa combinazione sigla + nome
    Optional<Provincia> findBySiglaAndNomeIgnoreCase(String sigla, String nome);
}
