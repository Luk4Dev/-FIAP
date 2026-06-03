package br.com.fiap.orbitagro.dto.dado;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DadoOrbitalRequest(
        @NotNull LocalDate dataColeta,
        @NotBlank @Size(max = 80) String fonte,
        @NotNull @DecimalMin("-1.0") @DecimalMax("1.0") Double indiceVegetacaoNdvi,
        @NotNull Double temperaturaSuperficie,
        @NotNull @DecimalMin("0.0") Double precipitacaoMm,
        @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double umidadeSoloPercentual,
        @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double indiceRiscoSeca,
        @NotNull Long regiaoRuralId
) {
}
