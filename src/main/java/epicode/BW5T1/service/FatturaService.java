package epicode.BW5T1.service;

import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.Cliente;
import epicode.BW5T1.model.Fattura;
import epicode.BW5T1.enumeration.StatoFattura;
import epicode.BW5T1.repository.ClienteRepository;
import epicode.BW5T1.repository.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Fattura saveFattura(Fattura fattura, int clienteId) throws NotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con id: " + clienteId));

        if (fatturaRepository.existsByNumeroAndClienteId(fattura.getNumero(), clienteId)) {
            throw new IllegalArgumentException("Fattura già esistente per questo cliente con lo stesso numero");
        }

        fattura.setCliente(cliente);
        return fatturaRepository.save(fattura);
    }

    public Fattura getFatturaById(int id) throws NotFoundException {
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata"));
    }

    public Page<Fattura> getAllFatture(Pageable pageable) {
        return fatturaRepository.findAll(pageable);
    }

    public Fattura updateFattura(int id, Fattura nuovaFattura) throws NotFoundException {
        Fattura esistente = getFatturaById(id);

        esistente.setData(nuovaFattura.getData());
        esistente.setImporto(nuovaFattura.getImporto());
        esistente.setNumero(nuovaFattura.getNumero());
        esistente.setStatoFattura(nuovaFattura.getStatoFattura());

        return fatturaRepository.save(esistente);
    }

    public void deleteFattura(int id) throws NotFoundException {
        Fattura fattura = getFatturaById(id);
        fatturaRepository.delete(fattura);
    }

    public Page<Fattura> getFattureByCliente(int clienteId, Pageable pageable) {
        return fatturaRepository.findByClienteId(clienteId, pageable);
    }

    public Page<Fattura> getFattureByStato(StatoFattura stato, Pageable pageable) {
        return fatturaRepository.findByStatoFattura(stato, pageable);
    }

    public Page<Fattura> getFattureByData(LocalDate data, Pageable pageable) {
        return fatturaRepository.findByData(data, pageable);
    }

    public Page<Fattura> getFattureByAnno(int anno, Pageable pageable) {
        return fatturaRepository.findByAnno(anno, pageable);
    }

    public Page<Fattura> getFattureByImportoBetween(double min, double max, Pageable pageable) {
        return fatturaRepository.findByImportoBetween(min, max, pageable);
    }
}
