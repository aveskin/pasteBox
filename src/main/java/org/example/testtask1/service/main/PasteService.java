package org.example.testtask1.service.main;

import org.example.testtask1.controller.dto.main.GetPasteDtoResponse;
import org.example.testtask1.controller.dto.main.SavePasteDtoRequest;
import org.example.testtask1.controller.dto.main.SavePasteDtoResponse;

import java.util.List;

public interface PasteService {

    GetPasteDtoResponse getByHash(String hash);

    List<GetPasteDtoResponse> getAll();

    SavePasteDtoResponse save(SavePasteDtoRequest savePasteDtoRequest);

    List<GetPasteDtoResponse> getAllWithAnyAccessRestriction();
}


