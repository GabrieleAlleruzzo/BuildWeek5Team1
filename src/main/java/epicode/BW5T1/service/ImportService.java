package epicode.BW5T1.service;

import epicode.BW5T1.model.Comune;
import epicode.BW5T1.model.Provincia;
import epicode.BW5T1.repository.ComuneRepository;
import epicode.BW5T1.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImportService {

    @Autowired
    private Comune comune;
    @Autowired
    private Provincia provincia;

    private final ProvinciaRepository provinciaRepository;
    private final ComuneRepository comuneRepository;

    public void importaProvince(File provinceCsv) throws IOException {
        List<String> lines = FileUtils.readLines(provinceCsv, StandardCharsets.UTF_8);
        lines.remove(0); // Rimuove intestazione

        for (String line : lines) {
            String[] tokens = line.split(";");
            if (tokens.length < 2) continue;

            String sigla = tokens[0].trim();
            String nome = tokens[1].trim();

            if (!provinciaRepository.existsByProvincia(provincia.getId())) {
                provinciaRepository.save(provincia);
            }

            List<Provincia> province = provinciaRepository.findBySiglaAndNomeIgnoreCase(sigla, nome);
            if (province.isEmpty()) {
                Provincia provincia = new Provincia();
                provincia.setSigla(sigla);
                provincia.setNome(nome);
                provinciaRepository.save(provincia);
            }
        }
    }

    public void importaComuni(File comuniCsv) throws IOException {
        List<String> lines = FileUtils.readLines(comuniCsv, StandardCharsets.UTF_8);
        lines.remove(0); // Rimuove intestazione

        for (String line : lines) {
            String[] tokens = line.split(";");
            if (tokens.length < 4) continue;

            String nomeComune = tokens[2].trim();
            String nomeProvincia = tokens[3].trim();

            List<Provincia> provinceTrovate = provinciaRepository.findByNomeIgnoreCase(nomeProvincia);

            if (provinceTrovate.isEmpty()) {
                System.err.println("Provincia non trovata per comune: " + nomeComune + " (" + nomeProvincia + ")");
                continue;
            }
            if (provinceTrovate.size() > 1) {
                System.err.println("Ambiguità: trovate più province con nome '" + nomeProvincia + "' per il comune '" + nomeComune + "'");
                continue;
            }
            if (!comuneRepository.existsByComune(comune.getId())) {
                comuneRepository.save(comune);
            }

            Provincia provincia = provinceTrovate.get(0);


            comuneRepository.findByNomeAndProvincia(nomeComune, provincia).orElseGet(() -> {
                Comune comune = new Comune();
                comune.setNome(nomeComune);
                comune.setProvincia(provincia);
                return comuneRepository.save(comune);
            });
        }
    }


}
