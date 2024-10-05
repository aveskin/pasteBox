package org.example.testtask1.repository;

import org.example.testtask1.entity.PasteBox;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PasteRepository extends JpaRepository<PasteBox, String> {

}
