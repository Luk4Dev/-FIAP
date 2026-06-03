package br.com.fiap.orbitagro.dto.rota;

import br.com.fiap.orbitagro.dto.common.CoordenadaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RotaAtendimentoRequest(
        @NotNull Long alertaId,
        @NotNull Long equipeId,
        @NotBlank @Size(max = 120) String origem,
        @NotBlank @Size(max = 120) String destino,
        @Valid @NotNull CoordenadaDTO coordenadaDestino,
        @NotNull @DecimalMin("0.1") Double distanciaKm,
        @NotNull @DecimalMin("1") Integer tempoEstimadoMinutos
) {
}
