package org.example.testtask1.service;

import lombok.RequiredArgsConstructor;
import org.example.testtask1.controller.dto.GetPasteDtoResponse;
import org.example.testtask1.controller.dto.SavePasteDtoRequest;
import org.example.testtask1.controller.dto.SavePasteDtoResponse;
import org.example.testtask1.entity.PasteBox;
import org.example.testtask1.exception.ElementNotFoundException;
import org.example.testtask1.exception.ListNotFoundException;
import org.example.testtask1.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;

    @Override
    public GetPasteDtoResponse getByHash(String hash) {
        PasteBox pasteBox = pasteRepository.findById(hash).orElseThrow(() -> new ElementNotFoundException(hash));

        return new GetPasteDtoResponse(pasteBox.getHash(),
                pasteBox.getPaste(),
                pasteBox.getExpirationTime(),
                pasteBox.getAccessRestriction());
    }

    @Override
    public List<GetPasteDtoResponse> getAll() {
        List<PasteBox> pasteBoxList = pasteRepository.findAll();
        if(pasteBoxList.isEmpty()){
            throw  new ListNotFoundException();
        }

        return pasteBoxList.stream()
                .limit(10)
                .map(paste ->
                        new GetPasteDtoResponse(paste.getHash(),
                                paste.getPaste(),
                                paste.getExpirationTime(),
                                paste.getAccessRestriction()))
                .collect(Collectors.toList());
    }

    @Override
    public SavePasteDtoResponse save(SavePasteDtoRequest s) {
        String hash = createNewHash(s);
        PasteBox pb = pasteRepository.save(new PasteBox(hash, s.getPaste(), s.getExpirationTime(), s.getAccessRestriction()));
        return new SavePasteDtoResponse(pb.getHash(), pb.getPaste(), pb.getExpirationTime(), pb.getAccessRestriction());
    }

    private String createNewHash(SavePasteDtoRequest s) {
        return String.valueOf(s.hashCode());
    }
}
