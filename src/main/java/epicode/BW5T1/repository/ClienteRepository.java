package epicode.BW5T1.repository;

import epicode.BW5T1.enumeration.TipoIndirizzo;
import epicode.BW5T1.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ClienteRepository  extends JpaRepository<Cliente, Integer>, PagingAndSortingRepository<Cliente, Integer> {



    Page<Cliente> findAllByOrderByRagioneSocialeAsc(Pageable pageable);
    Page<Cliente> findAllByOrderByFatturatoAnnualeDesc(Pageable pageable);
    Page<Cliente> findAllByOrderByDataInserimentoDesc(Pageable pageable);
    Page<Cliente> findAllByOrderByDataUltimoContattoDesc(Pageable pageable);
//    @Query("SELECT c FROM Cliente c JOIN Indirizzo i WHERE i.tipoIndirizzo = 'SEDE_LEGALE' ORDER BY i.provincia.nome")
//    Page<Cliente> findAllOrderByProvinciaSedeLegale(Pageable pageable);

    //inserire metodo per ordinare la  Provincia della sede legale (query).
    @Query("""
    SELECT c FROM Cliente c
    JOIN c.indirizzi i
    JOIN i.comune com
    JOIN com.provincia p
    WHERE i.tipoIndirizzo = :tipoIndirizzo
    ORDER BY p.nome ASC
""")
    Page<Cliente> findAllOrderByProvinciaSedeLegale(@Param("tipoIndirizzo") TipoIndirizzo tipoIndirizzo, Pageable pageable);




    Page<Cliente> findByFatturatoAnnuale(BigDecimal fatturatoAnnuale, Pageable pageable);
    Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable pageable);
    Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable pageable);
    Page<Cliente> findByRagioneSocialeContainingIgnoreCase(String keyword, Pageable pageable);;

}
