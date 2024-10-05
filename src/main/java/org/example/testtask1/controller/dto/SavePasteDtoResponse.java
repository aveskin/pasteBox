package org.example.testtask1.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.testtask1.entity.AccessRestriction;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SavePasteDtoResponse {
    private String hash;
    private String paste;
    private Long expirationTime;
    private AccessRestriction accessRestriction;
}
