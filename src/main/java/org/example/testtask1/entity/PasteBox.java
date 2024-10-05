package org.example.testtask1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasteBox {
    @Id
    private String hash;
    private String paste;

    @Column(nullable = false)
    private Long expirationTimeMin;
    @Column(nullable = false)
    private AccessRestriction  accessRestriction;
    @Column(nullable = false)
    private LocalDateTime createdAt;



}
