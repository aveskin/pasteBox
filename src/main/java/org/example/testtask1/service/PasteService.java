package org.example.testtask1.service;

import org.example.testtask1.controller.dto.GetPasteDtoResponse;
import org.example.testtask1.controller.dto.SavePasteDtoRequest;
import org.example.testtask1.controller.dto.SavePasteDtoResponse;

import java.util.List;

public interface PasteService {

    GetPasteDtoResponse getByHash(String hash);

    List<GetPasteDtoResponse> getAll();

    SavePasteDtoResponse save(SavePasteDtoRequest savePasteDtoRequest);
}

