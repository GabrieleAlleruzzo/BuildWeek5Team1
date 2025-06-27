package epicode.BW5T1.controller;

import epicode.BW5T1.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/import")
@RequiredArgsConstructor
public class ImportController {

    private final ImportService importService;

    @PostMapping("/province")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> importaProvince(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("province", ".csv");
            file.transferTo(tempFile);
            importService.importaProvince(tempFile);
            tempFile.delete(); // pulizia file temporaneo
            return ResponseEntity.ok("Province importate con successo.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Errore durante l'importazione delle province: " + e.getMessage());
        }
    }

    @PostMapping("/comuni")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> importaComuni(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("comuni", ".csv");
            file.transferTo(tempFile);
            importService.importaComuni(tempFile);
            tempFile.delete(); // pulizia file temporaneo
            return ResponseEntity.ok("Comuni importati con successo.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Errore durante l'importazione dei comuni: " + e.getMessage());
        }
    }
}
