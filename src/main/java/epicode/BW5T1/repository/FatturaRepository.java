package epicode.BW5T1.repository;

import epicode.BW5T1.model.Fattura;
import epicode.BW5T1.enumeration.StatoFattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface FatturaRepository extends JpaRepository<Fattura, Integer> {

    boolean existsByNumeroAndClienteId(String numero, int clienteId);

    Page<Fattura> findByClienteId(int clienteId, Pageable pageable);

    Page<Fattura> findByStatoFattura(StatoFattura statoFattura, Pageable pageable);

    Page<Fattura> findByData(LocalDate data, Pageable pageable);

    @Query("SELECT f FROM Fattura f WHERE YEAR(f.data) = :anno")
    Page<Fattura> findByAnno(@Param("anno") int anno, Pageable pageable);

    Page<Fattura> findByImportoBetween(double min, double max, Pageable pageable);
}
