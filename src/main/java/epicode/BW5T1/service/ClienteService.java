package epicode.BW5T1.service;


import com.cloudinary.Cloudinary;
import epicode.BW5T1.dto.ClienteDto;

import epicode.BW5T1.enumeration.TipoIndirizzo;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.Cliente;
import epicode.BW5T1.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    @Autowired
    private Cloudinary cloudinary;


    public Cliente saveCliente(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setRagioneSociale(clienteDto.getRagioneSociale());
        cliente.setPartitaIva(clienteDto.getPartitaIva());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setDataInserimento(clienteDto.getDataInserimento());
        cliente.setDataUltimoContatto(clienteDto.getDataUltimoContatto());
        cliente.setFatturatoAnnuale(clienteDto.getFatturatoAnnuale());
        cliente.setPec(clienteDto.getPec());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setEmailContatto(clienteDto.getEmailContatto());
        cliente.setNomeContatto(clienteDto.getNomeContatto());
        cliente.setCognomeContatto(clienteDto.getCognomeContatto());
        cliente.setTelefonoContatto(clienteDto.getTelefonoContatto());
        cliente.setLogoAziendale(clienteDto.getLogoAziendale());
        cliente.setTipoCliente(clienteDto.getTipoCliente());

        return clienteRepository.save(cliente);

    }

    public Cliente getCliente(int id) throws NotFoundException {
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("cliente con id " + id + " non trovato"));

    }

    public Page<Cliente> getAllClienti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clienteRepository.findAll(pageable);
    }


    public Cliente updateCliente(int id, ClienteDto clienteDto) throws NotFoundException {
        Cliente clienteDaAggiornare = getCliente(id);

        clienteDaAggiornare.setRagioneSociale(clienteDto.getRagioneSociale());
        clienteDaAggiornare.setPartitaIva(clienteDto.getPartitaIva());
        clienteDaAggiornare.setEmail(clienteDto.getEmail());
        clienteDaAggiornare.setDataInserimento(clienteDto.getDataInserimento());
        clienteDaAggiornare.setDataUltimoContatto(clienteDto.getDataUltimoContatto());
        clienteDaAggiornare.setFatturatoAnnuale(clienteDto.getFatturatoAnnuale());
        clienteDaAggiornare.setPec(clienteDto.getPec());
        clienteDaAggiornare.setTelefono(clienteDto.getTelefono());
        clienteDaAggiornare.setEmailContatto(clienteDto.getEmailContatto());
        clienteDaAggiornare.setNomeContatto(clienteDto.getNomeContatto());
        clienteDaAggiornare.setCognomeContatto(clienteDto.getCognomeContatto());
        clienteDaAggiornare.setTelefonoContatto(clienteDto.getTelefonoContatto());
        clienteDaAggiornare.setLogoAziendale(clienteDto.getLogoAziendale());
        clienteDaAggiornare.setTipoCliente(clienteDto.getTipoCliente());

        return (clienteRepository.save(clienteDaAggiornare));

    }

    public void deleteCliente(int id) throws NotFoundException {
        Cliente clienteDaRimuovere = getCliente(id);

        clienteRepository.delete(clienteDaRimuovere);
    }


    public Page<Cliente> getClientiOrderByRagioneSociale(Pageable pageable){
        return clienteRepository.findAllByOrderByRagioneSocialeAsc(pageable);

    }

    public Page<Cliente> getClientiOrderByFatturato(Pageable pageable){
        return clienteRepository.findAllByOrderByFatturatoAnnualeDesc(pageable);

    }


    public Page<Cliente> getClientiOrderDataInserimento(Pageable pageable){
        return clienteRepository.findAllByOrderByDataInserimentoDesc(pageable);

    }

    public Page<Cliente> getClientiOrderByDataUltimoContatto(Pageable pageable){
        return clienteRepository.findAllByOrderByDataUltimoContattoDesc(pageable);

    }


    public Page<Cliente> getClientiOrderByProvinciaSedeLegale(Pageable pageable) {
        return clienteRepository.findAllOrderByProvinciaSedeLegale(TipoIndirizzo.SEDE_LEGALE, pageable);
    }



    public Page<Cliente> getClientiByFatturato(BigDecimal fatturato, Pageable pageable){
        return clienteRepository.findByFatturatoAnnuale(fatturato,pageable);
    }

    public Page<Cliente> getClientiByDataInserimento(LocalDate data, Pageable pageable){
        return clienteRepository.findByDataInserimento(data, pageable);
    }

    public Page<Cliente> getClientiByUltimoContatto(LocalDate data, Pageable pageable){
        return clienteRepository.findByDataUltimoContatto(data, pageable);
    }

    public Page<Cliente> searchClientiByRagioneSociale(String keyword, Pageable pageable){
        return clienteRepository.findByRagioneSocialeContainingIgnoreCase(keyword, pageable);
    }

    public String patchLogoAziendale(int id, MultipartFile file) throws IOException, NotFoundException {
        Cliente clienteDaPatchare = getCliente(id);

        String url = (String) cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");

        clienteDaPatchare.setLogoAziendale(url);

        clienteRepository.save(clienteDaPatchare);

        return url;


    }

}



