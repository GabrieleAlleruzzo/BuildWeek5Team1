package epicode.BW5T1.controller;

import epicode.BW5T1.dto.IndirizzoDto;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.exception.ValidationException;
import epicode.BW5T1.model.Indirizzo;
import epicode.BW5T1.service.IndirizzoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;

    @PostMapping
    public Indirizzo createIndirizzo(@RequestBody @Validated IndirizzoDto indirizzoDto, BindingResult bindingResult)throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e,s)->e+s));
        }

        return indirizzoService.saveIndirizzo(indirizzoDto);
    }

    @GetMapping
    public Page<Indirizzo> getIndirizzi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return indirizzoService.getIndirizzi(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Indirizzo getIndirizzo(@PathVariable int id) throws NotFoundException {
        return indirizzoService.getIndirizzo(id);
    }

    @PutMapping("/{id}")
    public Indirizzo updateIndirizzo(@PathVariable int id, @RequestBody @Valid IndirizzoDto indirizzoDto) throws NotFoundException {
        return indirizzoService.updateIndirizzo(id, indirizzoDto);
    }

    @DeleteMapping("/{id}")
    public void deleteIndirizzo(@PathVariable int id) throws NotFoundException {
        indirizzoService.deleteIndirizzo(id);
    }

}
