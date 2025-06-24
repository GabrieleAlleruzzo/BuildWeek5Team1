package epicode.BW5T1.repository;

import epicode.BW5T1.model.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FatturaRepository extends JpaRepository<Fattura, Integer> {

    boolean existsByNumeroAndClienteId(String numero, int clienteId);



}
