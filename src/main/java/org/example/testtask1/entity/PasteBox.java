package org.example.testtask1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PasteBox {
    @Id
    private String hash;
    private String paste;
    private Long expirationTime;
    private AccessRestriction  accessRestriction;
}
