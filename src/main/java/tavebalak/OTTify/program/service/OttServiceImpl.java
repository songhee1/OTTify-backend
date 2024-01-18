package tavebalak.OTTify.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.program.dto.response.OttDTO;
import tavebalak.OTTify.program.dto.response.OttListDTO;
import tavebalak.OTTify.program.repository.OttRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OttServiceImpl implements OttService {

    private final OttRepository ottRepository;

    @Override
    public OttListDTO getOttList() {
        List<OttDTO> ottDTOList = ottRepository.findAll().stream()
                .map(OttDTO::new)
                .collect(Collectors.toList());

        return new OttListDTO(ottDTOList);
    }
}
