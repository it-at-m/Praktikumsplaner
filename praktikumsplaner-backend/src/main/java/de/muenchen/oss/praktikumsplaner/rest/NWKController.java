package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.service.ExcelService;
import de.muenchen.oss.praktikumsplaner.service.NWKService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/excel")
public class NWKController {
    private final NWKService nwkService;
    private final ExcelService excelService;

    @PostMapping("/")
    public void saveNWKExcel(@RequestBody String fileString) throws IOException {
        nwkService.saveNWK(excelService.excelToNwkDTOList(fileString));
    }
}
