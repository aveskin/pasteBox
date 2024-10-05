package org.example.testtask1.repository;

import org.example.testtask1.entity.PasteBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasteRepository extends JpaRepository<PasteBox, String> {

//    List<PasteBox> findAllPublic();
}
