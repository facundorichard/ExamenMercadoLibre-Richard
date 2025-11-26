package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void testGetStats_WithMutantsAndHumans() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(40, response.getCount_mutant_dna());
        assertEquals(100, response.getCount_human_dna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    void testGetStats_NoHumans() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(10, response.getCount_mutant_dna());
        assertEquals(0, response.getCount_human_dna());
        assertEquals(1.0, response.getRatio());
    }

    @Test
    void testGetStats_NoData() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(0, response.getCount_mutant_dna());
        assertEquals(0, response.getCount_human_dna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void testGetStats_LargeNumbers() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(1000000L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(2000000L);

        StatsResponse response = statsService.getStats();

        assertEquals(1000000, response.getCount_mutant_dna());
        assertEquals(2000000, response.getCount_human_dna());
        assertEquals(0.5, response.getRatio());
    }

    @Test
    void testGetStats_ZeroMutants() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(0, response.getCount_mutant_dna());
        assertEquals(100, response.getCount_human_dna());
        assertEquals(0.0, response.getRatio());
    }
}
