package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.repository.NWKRepository;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NWKService {

    private final NWKMapper nwkMapper;
    private final NWKRepository nwkRepository;
    private final ExcelService excelService;

    public NwkDTO saveNWK(CreateNwkDTO createNwkDTO) {
        return nwkMapper.toDTO(nwkRepository.save(nwkMapper.toEntity(createNwkDTO)));
    }

    public void importNWK(String base64String) throws IOException {
        excelService.excelToNwkDTOList(base64String).forEach(this::saveNWK);
    }

    public Iterable<NWK> findAllActiveNWKs() {
        return nwkRepository.findNWKsByIsActiveIsTrueOrderByNachname();
    }
}
