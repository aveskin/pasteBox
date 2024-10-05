package org.example.testtask1.service;

import lombok.RequiredArgsConstructor;
import org.example.testtask1.config.AppConfig;
import org.example.testtask1.controller.dto.GetPasteDtoResponse;
import org.example.testtask1.controller.dto.SavePasteDtoRequest;
import org.example.testtask1.controller.dto.SavePasteDtoResponse;
import org.example.testtask1.entity.AccessRestriction;
import org.example.testtask1.entity.PasteBox;
import org.example.testtask1.exception.ElementNotFoundException;
import org.example.testtask1.exception.LifeExpiredException;
import org.example.testtask1.exception.ListNotFoundException;
import org.example.testtask1.repository.PasteRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;
    private final AppConfig appConfig;

    @Override
    public GetPasteDtoResponse getByHash(String hash) {
        PasteBox pasteBox = pasteRepository.findById(hash).orElseThrow(() -> new ElementNotFoundException(hash));
        if (isPasteAlive(pasteBox)) {
            return new GetPasteDtoResponse(pasteBox.getPaste(),appConfig.getPasteApiUrl() + pasteBox.getHash());
        }
        throw new LifeExpiredException(hash);
    }

    @Override
    public List<GetPasteDtoResponse> getAll() {
        List<PasteBox> pasteBoxList = pasteRepository.findAll();
        if (!pasteBoxList.isEmpty()) {
            List<GetPasteDtoResponse> response = pasteBoxList
                    .stream()
                    .filter(pasteBox -> pasteBox.getAccessRestriction().equals(AccessRestriction.PUBLIC_ACCESS))
                    .sorted(Comparator.comparing(PasteBox::getCreatedAt).reversed())
                    .limit(10)
                    .map(pasteBox -> new GetPasteDtoResponse(pasteBox.getPaste(),
                                                    appConfig.getPasteApiUrl() + pasteBox.getHash()))
                    .toList();
            if (!response.isEmpty()) {
                return response;
            }
        }
        throw new ListNotFoundException();
    }

    @Override
    public SavePasteDtoResponse save(SavePasteDtoRequest s) {
        PasteBox pasteBox = pasteRepository.save(new PasteBox(
                String.valueOf(s.hashCode()),
                s.getPaste(),
                s.getExpirationTimeMin(),
                s.getAccessRestriction(),
                LocalDateTime.now()));
        return new SavePasteDtoResponse( appConfig.getPasteApiUrl() + pasteBox.getHash());
    }

    private boolean isPasteAlive(PasteBox pasteBox) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime creationTime = pasteBox.getCreatedAt();
        Duration duration = Duration.between(creationTime, currentTime);
        return duration.toMinutes() < pasteBox.getExpirationTimeMin();
    }
}
