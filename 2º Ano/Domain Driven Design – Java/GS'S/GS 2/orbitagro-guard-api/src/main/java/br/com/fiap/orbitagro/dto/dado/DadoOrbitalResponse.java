package br.com.fiap.orbitagro.dto.dado;

import java.time.LocalDate;

public record DadoOrbitalResponse(
        Long id,
        LocalDate dataColeta,
        String fonte,
        Double indiceVegetacaoNdvi,
        Double temperaturaSuperficie,
        Double precipitacaoMm,
        Double umidadeSoloPercentual,
        Double indiceRiscoSeca,
        Long regiaoRuralId,
        String regiaoRuralNome
) {
}
