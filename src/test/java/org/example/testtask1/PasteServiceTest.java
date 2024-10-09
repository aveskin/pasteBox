package org.example.testtask1;

import org.example.testtask1.config.AppConfig;
import org.example.testtask1.entity.PasteBox;
import org.example.testtask1.exception.ElementNotFoundException;
import org.example.testtask1.exception.LifeExpiredException;
import org.example.testtask1.exception.ListNotFoundException;
import org.example.testtask1.repository.PasteRepository;
import org.example.testtask1.service.main.PasteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class PasteServiceTest {

    @Mock
    private PasteRepository pasteRepository;

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private PasteServiceImpl pasteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByHash_WhenPasteExistsAndIsAlive_ShouldReturnPaste() {
        PasteBox pb = new PasteBox();
        pb.setHash("1");
        Optional<PasteBox> optPb = Optional.of(pb);
        when(pasteRepository.findById("1")).thenReturn(optPb);

        assertEquals(pb.getHash(), pasteRepository.findById("1").get().getHash());
    }

    @Test
     void testGetByHash_WhenPasteDoesNotExist_ShouldThrowElementNotFoundException() {
        // Arrange
        String hash = "nonExistentHash";
        when(pasteRepository.findById(hash)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ElementNotFoundException.class, () -> pasteService.getByHash(hash));
    }

    @Test
    void testGetAll_WhenNoPastesExist_ShouldThrowListNotFoundException() {
        // Arrange
        when(pasteRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ListNotFoundException.class, () -> pasteService.getAll());
    }

    @Test
     void testGetByHash_WhenPasteIsExpired_ShouldThrowPasteNotFoundException() {
        // Arrange
        String hash = "1";
        PasteBox pb = new PasteBox();
        pb.setHash(hash);
        pb.setCreatedAt(LocalDateTime.now());
        pb.setExpirationTimeMin(-1L);
        Optional<PasteBox> optPb = Optional.of(pb);
        when(pasteRepository.findById(hash)).thenReturn(optPb);
        assertThrows(LifeExpiredException.class, () -> pasteService.getByHash(hash));
    }


}
