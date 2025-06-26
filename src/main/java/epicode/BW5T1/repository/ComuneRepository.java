package epicode.BW5T1.repository;

import epicode.BW5T1.model.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ComuneRepository extends JpaRepository<Comune, Long> {

    @Query("SELECT c FROM Comune c WHERE c.nome = :nome AND c.provincia.id = :provinciaId")
    Optional<Comune> findByNomeAndProvinciaCustom(@Param("nome") String nome, @Param("provinciaId") Long provinciaId);
}
