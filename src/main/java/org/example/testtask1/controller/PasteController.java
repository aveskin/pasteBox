package org.example.testtask1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testtask1.controller.dto.GetPasteDtoResponse;
import org.example.testtask1.controller.dto.SavePasteDtoRequest;
import org.example.testtask1.controller.dto.SavePasteDtoResponse;
import org.example.testtask1.service.PasteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paste-api")
public class PasteController {
    private final PasteService pasteService;

    @PostMapping("/save")
    public ResponseEntity<SavePasteDtoResponse> savePaste(
            @RequestBody @Valid SavePasteDtoRequest savePasteDtoRequest, UriComponentsBuilder uriComponentsBuilder){

        SavePasteDtoResponse paste = pasteService.save(savePasteDtoRequest);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/paste-api/{url}")
                        .build(Map.of("url", paste.getUrl())))
                .body(paste);
    }

    @GetMapping("/{hash}")
    public GetPasteDtoResponse getPaste(@PathVariable("hash") String hash){
       return pasteService.getByHash(hash);
    }

    @GetMapping("/list")
    public List<GetPasteDtoResponse> getListPaste(){
        return pasteService.getAll();
    }

}
