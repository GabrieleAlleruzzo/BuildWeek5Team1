package epicode.BW5T1.controller;


import epicode.BW5T1.dto.ClienteDto;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.exception.ValidationException;
import epicode.BW5T1.model.Cliente;
import epicode.BW5T1.service.ClienteService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping(path = "/clienti")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@RequestBody @Validated ClienteDto clienteDto, BindingResult bindingResult)throws ValidationException, NotFoundException {

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e,s)->e+s));
        }
        return clienteService.saveCliente(clienteDto);

    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Page<Cliente> getAllClienti(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy){

        return clienteService.getAllClienti(page, size, sortBy);
    }


    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable int id) throws NotFoundException {
        return clienteService.getCliente(id);
    }


    @PutMapping ("/{id}")
    public Cliente updateCliente(@PathVariable int id,@RequestBody @Validated ClienteDto clienteDto, BindingResult bindingResult)throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e,s)->e+s));
        }
        return clienteService.updateCliente(id, clienteDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable int id) throws NotFoundException {
        clienteService.deleteCliente(id);
    }

    @PatchMapping("/{id}/profile-image")
    public String uploadProfileImage(@PathVariable int id,@RequestBody @NotNull MultipartFile file) throws NotFoundException, IOException {
        return clienteService.patchLogoAziendale(id, file);


    }

    @GetMapping("/order/ragione-sociale")
    public Page<Cliente> getClientiOrderByRagioneSociale(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteService.getClientiOrderByRagioneSociale(pageable);
    }

    @GetMapping("/order/fatturato")
    public Page<Cliente> getClientiOrderByFatturato(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteService.getClientiOrderByFatturato(pageable);
    }

    @GetMapping("/order/data-inserimento")
    public Page<Cliente> getClientiOrderByDataInserimento(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteService.getClientiOrderDataInserimento(pageable);
    }

    @GetMapping("/order/data-ultimo-contatto")
    public Page<Cliente> getClientiOrderByDataUltimoContatto(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteService.getClientiOrderByDataUltimoContatto(pageable);
    }

    @GetMapping("/ordina/provincia-sede-legale")
    public Page<Cliente> getClientiOrdinatiPerProvincia(Pageable pageable) {
        return clienteService.getClientiOrderByProvinciaSedeLegale(pageable);
    }


    @GetMapping("/filter/fatturato")
    public Page<Cliente> getClientiByFatturato(
            @RequestParam(required = false) String fatturato,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            if (fatturato == null || fatturato.trim().isEmpty()) {
                Pageable pageable = PageRequest.of(page, size);
                return clienteService.getAllClienti(page, size, "id");
            }
            BigDecimal valore = new BigDecimal(fatturato.replace(",", "."));
            Pageable pageable = PageRequest.of(page, size);
            return clienteService.getClientiByFatturato(valore, pageable);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Formato fatturato non valido. Utilizzare un numero valido (es: 1000.50 o 1000,50)");
        }
    }

    @GetMapping("/filter/data-inserimento")
    public Page<Cliente> getClientiByDataInserimento(
            @RequestParam String data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteService.getClientiByDataInserimento(LocalDate.parse(data), pageable);
    }

    @GetMapping("/filter/data-ultimo-contatto")
    public Page<Cliente> getClientiByUltimoContatto(
            @RequestParam String data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteService.getClientiByUltimoContatto(LocalDate.parse(data), pageable);
    }

    @GetMapping("/search")
    public Page<Cliente> searchClientiByRagioneSociale(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteService.searchClientiByRagioneSociale(keyword, pageable);
    }








}
