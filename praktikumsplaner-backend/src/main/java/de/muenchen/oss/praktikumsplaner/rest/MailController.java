package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.ZeitraumDto;
import de.muenchen.oss.praktikumsplaner.service.MailService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    private final String SUCCESSFUL_ASSIGNMENT = "successful";

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PostMapping("/send")
    public ResponseEntity<?> sendMailsToAusbilderinnen(@RequestParam(name = "assignmentStatus") String assignmentStatus,
            @RequestBody Map<String, ZeitraumDto> assignmentPeriods) {
        if (assignmentStatus.equals(SUCCESSFUL_ASSIGNMENT)) {
            List<PraktikumsstelleDto> faultyStellen = mailService.sendMailsToAssignedPraktikumsplaetze(assignmentPeriods);

            if (faultyStellen.isEmpty()) {
                return ResponseEntity.ok().build();
            } else
                return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(faultyStellen);
        } else {
            throw new IllegalArgumentException("AssignmentStatus-Parameter nicht unterstützt.");
        }
    }
}
