package org.example.testtask1.controller.dto.main;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.testtask1.entity.AccessRestriction;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SavePasteDtoRequest {
    @NotBlank(message = "Поле обязательно для заполения")
    private String paste;
    private Long expirationTimeMin;
    private AccessRestriction accessRestriction;
}
