package epicode.BW5T1.controller;

import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.Fattura;
import epicode.BW5T1.enumeration.StatoFattura;
import epicode.BW5T1.service.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    @PostMapping("/cliente/{clienteId}")
    public Fattura creaFattura(@RequestBody Fattura fattura, @PathVariable int clienteId) throws NotFoundException {
        return fatturaService.saveFattura(fattura, clienteId);
    }

    @GetMapping
    public Page<Fattura> getAllFatture(Pageable pageable) {
        return fatturaService.getAllFatture(pageable);
    }

    @GetMapping("/{id}")
    public Fattura getFatturaById(@PathVariable int id) throws NotFoundException {
        return fatturaService.getFatturaById(id);
    }

    @PutMapping("/{id}")
    public Fattura aggiornaFattura(@PathVariable int id, @RequestBody Fattura fattura) throws NotFoundException {
        return fatturaService.updateFattura(id, fattura);
    }

    @DeleteMapping("/{id}")
    public void cancellaFattura(@PathVariable int id) throws NotFoundException {
        fatturaService.deleteFattura(id);
    }

    // FILTRI
    @GetMapping("/filter/cliente/{clienteId}")
    public Page<Fattura> getFattureByCliente(@PathVariable int clienteId, Pageable pageable) {
        return fatturaService.getFattureByCliente(clienteId, pageable);
    }

    @GetMapping("/filter/stato")
    public Page<Fattura> getFattureByStato(@RequestParam StatoFattura stato, Pageable pageable) {
        return fatturaService.getFattureByStato(stato, pageable);
    }

    @GetMapping("/filter/data")
    public Page<Fattura> getFattureByData(@RequestParam String data, Pageable pageable) {
        return fatturaService.getFattureByData(LocalDate.parse(data), pageable);
    }

    @GetMapping("/filter/anno")
    public Page<Fattura> getFattureByAnno(@RequestParam int anno, Pageable pageable) {
        return fatturaService.getFattureByAnno(anno, pageable);
    }

    @GetMapping("/filter/importo")
    public Page<Fattura> getFattureByImportoRange(@RequestParam double min,
                                                  @RequestParam double max,
                                                  Pageable pageable) {
        return fatturaService.getFattureByImportoBetween(min, max, pageable);
    }
}
