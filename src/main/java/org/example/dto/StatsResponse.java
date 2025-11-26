package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Estadísticas de verificaciones de ADN")
public class StatsResponse {

    @Schema(description = "Cantidad de ADN mutante verificado")
    private long count_mutant_dna;

    @Schema(description = "Cantidad de ADN humano verificado")
    private long count_human_dna;

    @Schema(description = "Ratio de mutantes (mutant / human)")
    private double ratio;
}
