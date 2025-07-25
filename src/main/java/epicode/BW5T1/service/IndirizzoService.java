package epicode.BW5T1.service;

import epicode.BW5T1.dto.IndirizzoDto;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.Cliente;
import epicode.BW5T1.model.Comune;
import epicode.BW5T1.model.Indirizzo;
import epicode.BW5T1.model.Provincia;
import epicode.BW5T1.repository.ClienteRepository;
import epicode.BW5T1.repository.ComuneRepository;
import epicode.BW5T1.repository.IndirizzoRepository;
import epicode.BW5T1.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;

    @Autowired
    private ComuneRepository comuneRepository; // da creare ComuneRepository

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private final Map<String, Provincia> provinciaCache = new HashMap<>();

    public Indirizzo saveIndirizzo(IndirizzoDto indirizzoDto) {

        Cliente cliente = clienteRepository.findById(Math.toIntExact(indirizzoDto.getIdCliente()))
                .orElseThrow(() -> new NotFoundException(
                        "Cliente id %d non trovato".formatted(indirizzoDto.getIdCliente())));
        Comune comune = comuneRepository.findById(indirizzoDto.getIdComune()) // errore presente perché bisogna creare ComuneRepository
                .orElseThrow(()-> new NotFoundException("Comune con id " + indirizzoDto.getIdComune() + " non trovato"));

        System.out.println(cliente);
        System.out.println(comune);
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(indirizzoDto.getVia());
        indirizzo.setCivico(indirizzoDto.getCivico());
        indirizzo.setCap(indirizzoDto.getCap());
        indirizzo.setCliente(cliente);
        indirizzo.setComune(comune);
        indirizzo.setTipoIndirizzo(indirizzoDto.getTipoIndirizzo());

        System.out.println(indirizzo);

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
        indirizzo.setCap(indirizzoDto.getCap());
        indirizzo.setComune(comune);
        indirizzo.setTipoIndirizzo(indirizzoDto.getTipoIndirizzo());

        return indirizzoRepository.save(indirizzo);
    }

    public void deleteIndirizzo(int id) throws NotFoundException {
        Indirizzo indirizzo = getIndirizzo(id);
        indirizzoRepository.delete(indirizzo);
    }

    public Provincia getProvinciaCached(String nomeProvincia) {
        String chiave = nomeProvincia.trim().toUpperCase();

        if (provinciaCache.containsKey(chiave)) {
            return provinciaCache.get(chiave);
        }

        Provincia provincia = provinciaRepository
                .findByNomeIgnoreCase(nomeProvincia).orElseThrow(() -> new NotFoundException("Provincia con nome " + nomeProvincia + " non trovata"));

        provinciaCache.put(chiave, provincia);
        return provincia;
    }

}
