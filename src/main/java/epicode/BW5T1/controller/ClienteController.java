package epicode.BW5T1.controller;


import epicode.BW5T1.dto.ClienteDto;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.exception.ValidationException;
import epicode.BW5T1.model.Cliente;
import epicode.BW5T1.service.ClienteService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

}
