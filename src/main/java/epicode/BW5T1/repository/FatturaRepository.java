package epicode.BW5T1.repository;

import epicode.BW5T1.enumeration.StatoFattura;
import epicode.BW5T1.model.Fattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FatturaRepository extends JpaRepository<Fattura, Integer> {

    boolean existsByNumeroAndClienteId(String numero, int clienteId);




}
