package de.muenchen.oss.praktikumsplaner.rest;

import static de.muenchen.oss.praktikumsplaner.security.Authorities.HAS_ROLE_AUSBILDUNGSLEITUNG;

import de.muenchen.oss.praktikumsplaner.service.ExcelExportService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("export")
@RequiredArgsConstructor
public class ExportController {

    private final ExcelExportService excelExportService;

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getExcelFileAsBase64() throws IOException {
        return excelExportService.getBase64EncodedExcelFile();
    }
}
