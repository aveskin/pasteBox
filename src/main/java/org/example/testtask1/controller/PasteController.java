package org.example.testtask1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testtask1.controller.dto.main.GetPasteDtoResponse;
import org.example.testtask1.controller.dto.main.SavePasteDtoRequest;
import org.example.testtask1.controller.dto.main.SavePasteDtoResponse;
import org.example.testtask1.service.main.PasteService;
import org.example.testtask1.service.main.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Операции пользователя")
@RequestMapping("/paste-api/secured")
public class PasteController {
    private final PasteService pasteService;
    private final UserService userService;


    @Operation(summary = "Сохранение пасты")
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

    @Operation(summary = "Получение пасты по hash")
    @GetMapping("/{hash}")
    public GetPasteDtoResponse getPaste(@PathVariable("hash") String hash){
       return pasteService.getByHash(hash);
    }

    @Operation(summary = "Получение списка последних 10 публичных паст")
    @GetMapping("/list")
    public List<GetPasteDtoResponse> getListPaste(){
        return pasteService.getAll();
    }

    @Operation(summary = "Получение списка всех паст(с любым AccessRestriction), доступен только пользователю с ролью ADMIN")
    @GetMapping("/list-all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetPasteDtoResponse> getListPasteWithAnyAccessRestriction(){
        return pasteService.getAllWithAnyAccessRestriction();
    }

    @Operation(summary = "Получение текущего пользователя")
    @GetMapping("/user")
    public String accessUser(Principal principal){
        if(principal==null){
            return null;
        }
        return principal.getName();
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN (для демонстрации)")
    public void getAdmin() {
        userService.getAdmin();
    }


}
