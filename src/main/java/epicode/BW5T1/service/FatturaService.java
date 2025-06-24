package epicode.BW5T1.service;

import epicode.BW5T1.dto.FatturaDto;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.Cliente;
import epicode.BW5T1.model.Fattura;
import epicode.BW5T1.repository.ClienteRepository;
import epicode.BW5T1.repository.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Fattura saveFattura(FatturaDto fatturaDto, int clienteId) throws NotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con id: " + clienteId));

        if (fatturaRepository.existsByNumeroAndClienteId(fatturaDto.getNumero(), clienteId)) {
            throw new IllegalArgumentException("Fattura già esistente per questo cliente con lo stesso numero");
        }
        Fattura fattura = new Fattura();
        fattura.setData(fatturaDto.getData());
        fattura.setImporto(fatturaDto.getImporto());
        fattura.setNumero(fatturaDto.getNumero());
        fattura.setStatoFattura(fatturaDto.getStatoFattura());

        return fatturaRepository.save(fattura);
    }

    public Fattura getFatturaById(int id) throws NotFoundException {
        return fatturaRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata"));
    }
    public Page<FatturaDto> getAllFatture(Pageable pageable) {
        return fatturaRepository.findAll(pageable)
                .map(this::mapToDto); // Usa il metodo manuale mapToDto
    }

    public Fattura updateFattura(int id, FatturaDto fatturaDto) throws NotFoundException {
        Fattura fatturaDaAggiornare = getFatturaById(id);

        fatturaDaAggiornare.setData(fatturaDto.getData());
        fatturaDaAggiornare.setImporto(fatturaDto.getImporto());
        fatturaDaAggiornare.setNumero(fatturaDto.getNumero());
        fatturaDaAggiornare.setStatoFattura(fatturaDto.getStatoFattura());

        return fatturaRepository.save(fatturaDaAggiornare);
    }

    public void deleteFattura(int id) throws NotFoundException{
        Fattura fattura = fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata con id: " + id));
        fatturaRepository.delete(fattura);
    }

    private FatturaDto mapToDto(Fattura fattura) {
        FatturaDto dto = new FatturaDto();
        dto.setData(fattura.getData());
        dto.setImporto(fattura.getImporto());
        dto.setNumero(fattura.getNumero());
        dto.setStatoFattura(fattura.getStatoFattura());
        return dto;
    }

}
