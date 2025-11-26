package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    @Captor
    private ArgumentCaptor<DnaRecord> dnaRecordCaptor;

    @Test
    void testAnalyzeDna_NewMutant() {
        String[] dna = { "AAAA", "CCCC", "GGGG", "TTTT" };
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        verify(dnaRecordRepository, times(1)).save(dnaRecordCaptor.capture());
        assertTrue(dnaRecordCaptor.getValue().isMutant());
    }

    @Test
    void testAnalyzeDna_NewHuman() {
        String[] dna = { "ATGC", "CAGT", "TTAT", "AGAC" };
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false);

        boolean result = mutantService.analyzeDna(dna);

        assertFalse(result);
        verify(dnaRecordRepository, times(1)).save(dnaRecordCaptor.capture());
        assertFalse(dnaRecordCaptor.getValue().isMutant());
    }

    @Test
    void testAnalyzeDna_ExistingMutant() {
        String[] dna = { "AAAA", "CCCC", "GGGG", "TTTT" };
        DnaRecord existing = new DnaRecord("hash", true);
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(existing));

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        verify(mutantDetector, never()).isMutant(dna);
        verify(dnaRecordRepository, never()).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDna_RepositoryException() {
        String[] dna = { "AAAA", "CCCC", "GGGG", "TTTT" };
        when(dnaRecordRepository.findByDnaHash(anyString())).thenThrow(new RuntimeException("DB Error"));

        try {
            mutantService.analyzeDna(dna);
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("DB Error"));
        }
    }
}
