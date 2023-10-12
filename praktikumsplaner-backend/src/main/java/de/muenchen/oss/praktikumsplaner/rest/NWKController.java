package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.service.ExcelService;
import de.muenchen.oss.praktikumsplaner.service.NWKService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/nachwuchskraft")
public class NWKController {
    private final NWKService nwkService;

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNWKExcel(@RequestBody String base64String) throws IOException {
        nwkService.importNWK(base64String);
    }
}
