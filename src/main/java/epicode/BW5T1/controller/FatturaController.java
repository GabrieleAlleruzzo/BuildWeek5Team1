package epicode.BW5T1.controller;


import epicode.BW5T1.dto.FatturaDto;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.Fattura;
import epicode.BW5T1.service.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    // ✅ CREATE
    @PostMapping("/cliente/{clienteId}")
    public Fattura creaFattura(@RequestBody FatturaDto fatturaDto, @PathVariable int clienteId) throws NotFoundException {
        return fatturaService.saveFattura(fatturaDto, clienteId);
    }
    // ✅ GET ALL paginato
    @GetMapping
    public Page<FatturaDto> getAllFatture(Pageable pageable) {
        return fatturaService.getAllFatture(pageable);
    }

    // ✅ GET by ID
    @GetMapping("/{id}")
    public Fattura getFatturaById(@PathVariable int id) throws NotFoundException {
        return fatturaService.getFatturaById(id);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Fattura aggiornaFattura(@PathVariable int id, @RequestBody FatturaDto fatturaDto) throws NotFoundException {
        return fatturaService.updateFattura(id, fatturaDto);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void cancellaFattura(@PathVariable int id) throws NotFoundException {
        fatturaService.deleteFattura(id);
    }

}
