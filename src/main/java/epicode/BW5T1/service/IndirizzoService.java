package epicode.BW5T1.service;

import epicode.BW5T1.dto.IndirizzoDto;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.Comune;
import epicode.BW5T1.model.Indirizzo;
import epicode.BW5T1.repository.ComuneRepository;
import epicode.BW5T1.repository.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;

    @Autowired
    private ComuneRepository comuneRepository; // da creare ComuneRepository

    public Indirizzo saveIndirizzo(IndirizzoDto indirizzoDto) {
        Comune comune = comuneRepository.findById(indirizzoDto.getIdComune()) // errore presente perché bisogna creare ComuneRepository
                .orElseThrow(()-> new NotFoundException("Comune con id " + indirizzoDto.getIdComune() + " non trovato"));

        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(indirizzoDto.getVia());
        indirizzo.setCivico(indirizzoDto.getCivico());
        indirizzo.setLocalita(indirizzoDto.getLocalita());
        indirizzo.setCap(indirizzoDto.getCap());
        indirizzo.setComune(comune);

        return indirizzoRepository.save(indirizzo);
    }

    // paginazione (restituisce una porzione di indirizzi alla volta) + ordinamento (mette in ordine i risultati, per esempio per via o località)
    public Page<Indirizzo> getIndirizzi(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return indirizzoRepository.findAll(pageable);
    }

    public Indirizzo getIndirizzo(int id) throws NotFoundException {
        return indirizzoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Indirizzo con id: " + id + " non trovato"));
    }

    public Indirizzo updateIndirizzo(int id, IndirizzoDto indirizzoDto) throws NotFoundException {
        Indirizzo indirizzo = getIndirizzo(id);

        Comune comune = comuneRepository.findById(indirizzoDto.getIdComune()) // errore presente perché bisogna creare ComuneRepository
                .orElseThrow(() -> new NotFoundException("Comune con id " + indirizzoDto.getIdComune() + " non trovato"));

        indirizzo.setVia(indirizzoDto.getVia());
        indirizzo.setCivico(indirizzoDto.getCivico());
        indirizzo.setLocalita(indirizzoDto.getLocalita());
        indirizzo.setCap(indirizzoDto.getCap());
        indirizzo.setComune(comune);

        return indirizzoRepository.save(indirizzo);
    }

    public void deleteIndirizzo(int id) throws NotFoundException {
        Indirizzo indirizzo = getIndirizzo(id);
        indirizzoRepository.delete(indirizzo);
    }
}
