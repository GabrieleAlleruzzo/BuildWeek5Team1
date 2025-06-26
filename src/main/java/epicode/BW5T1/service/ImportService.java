package epicode.BW5T1.service;

import epicode.BW5T1.model.Comune;
import epicode.BW5T1.model.Provincia;
import epicode.BW5T1.repository.ComuneRepository;
import epicode.BW5T1.repository.ProvinciaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final ProvinciaRepository provinciaRepository;
    private final ComuneRepository comuneRepository;

    // Cache per evitare query duplicate sulle province
    private final Map<String, Provincia> provinciaCache = new HashMap<>();

    @PersistenceContext
    private EntityManager entityManager;

    public void importaProvince(File provinceCsv) throws IOException {
        List<String> lines = FileUtils.readLines(provinceCsv, StandardCharsets.UTF_8);
        if (!lines.isEmpty()) lines.remove(0); // Rimuove intestazione

        for (String line : lines) {
            String[] tokens = line.split(";");
            if (tokens.length < 2) continue;

            String sigla = tokens[0].trim();
            String nome = tokens[1].trim();
            String chiave = (sigla + "|" + nome).toUpperCase();

            if (provinciaCache.containsKey(chiave)) continue;

            provinciaRepository.findBySiglaAndNomeIgnoreCase(sigla, nome)
                    .ifPresentOrElse(
                            provincia -> provinciaCache.put(chiave, provincia),
                            () -> {
                                Provincia nuova = new Provincia();
                                nuova.setSigla(sigla);
                                nuova.setNome(nome);
                                Provincia salvata = provinciaRepository.save(nuova);
                                provinciaCache.put(chiave, salvata);
                            }
                    );
        }
    }

    public void importaComuni(File comuniCsv) throws IOException {
        List<String> lines = FileUtils.readLines(comuniCsv, StandardCharsets.UTF_8);
        if (!lines.isEmpty()) lines.remove(0); // Rimuove intestazione

        for (String line : lines) {
            String[] tokens = line.split(";");
            if (tokens.length < 4) continue;

            String nomeComune = tokens[2].trim();
            String nomeProvincia = tokens[3].trim();

            // Cerca nella cache una provincia con nome corrispondente (ignorando maiuscole/minuscole)
            Provincia provincia = provinciaCache.values().stream()
                    .filter(p -> p.getNome().equalsIgnoreCase(nomeProvincia))
                    .findFirst()
                    .orElseGet(() -> {
                        Optional<Provincia> trovata = provinciaRepository.findByNomeIgnoreCase(nomeProvincia);
                        if (trovata.isPresent()) {
                            Provincia p = trovata.get();
                            String cacheKey = (p.getSigla() + "|" + p.getNome()).toUpperCase();
                            provinciaCache.put(cacheKey, p);
                            return p;
                        }
                        return null;
                    });

            if (provincia == null) {
                System.err.println("Provincia non trovata per comune: " + nomeComune + " (" + nomeProvincia + ")");
                continue;
            }

            // Recupera la proxy per evitare fetch dell'intera entità
            Provincia provinciaProxy = entityManager.getReference(Provincia.class, provincia.getId());

            comuneRepository.findByNomeAndProvinciaCustom(nomeComune, provinciaProxy.getId())
                    .orElseGet(() -> {
                        Comune nuovo = new Comune();
                        nuovo.setNome(nomeComune);
                        nuovo.setProvincia(provinciaProxy);
                        return comuneRepository.save(nuovo);
                    });
        }
    }
}
