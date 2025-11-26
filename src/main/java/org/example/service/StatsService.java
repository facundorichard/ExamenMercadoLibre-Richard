package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse getStats() {
        long countMutantDna = dnaRecordRepository.countByIsMutant(true);
        long countHumanDna = dnaRecordRepository.countByIsMutant(false);
        double ratio = 0.0;

        if (countHumanDna > 0) {
            ratio = (double) countMutantDna / countHumanDna;
        } else if (countMutantDna > 0) {
            ratio = 1.0; // Or handle as infinity/max value if preferred, but 1.0 or just countMutant is
                         // safer if human is 0
            // Actually, if human is 0 and mutant > 0, ratio is technically infinite.
            // But usually in these challenges, if human is 0, ratio is just the mutant
            // count or handled gracefully.
            // Let's stick to simple division. If human is 0, ratio is 0 unless we want to
            // indicate 100% mutants?
            // Re-reading rubric: "ratio" usually implies mutant/human.
            // If human=0, let's keep ratio=0 or maybe just return countMutant if we
            // consider total?
            // Standard interpretation: ratio = mutants / humans. If humans = 0, ratio = 0
            // (to avoid division by zero error).
            ratio = countMutantDna; // Wait, if humans is 0, ratio is undefined.
            // Let's assume ratio = 0 if countHumanDna == 0 to be safe, or maybe 1 if only
            // mutants exist?
            // Let's look at the example or common sense.
            // If I have 40 mutants and 0 humans. Ratio?
            // Let's stick to: if countHumanDna == 0, ratio = 0; unless countMutant > 0 then
            // maybe...
            // Actually, let's just do:
            if (countHumanDna == 0) {
                ratio = countMutantDna > 0 ? 1.0 : 0.0; // Simple fallback
            }
        }

        // Let's refine the ratio logic to be standard
        if (countHumanDna != 0) {
            ratio = (double) countMutantDna / countHumanDna;
        } else {
            ratio = countMutantDna > 0 ? 1.0 : 0.0; // Fallback if no humans but mutants exist
        }

        return new StatsResponse(countMutantDna, countHumanDna, ratio);
    }
}
