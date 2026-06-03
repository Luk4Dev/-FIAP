package br.com.fiap.orbitagro.dto.common;

import jakarta.validation.constraints.DecimalMin;

public record AreaMonitoradaDTO(
        @DecimalMin("0.1") Double hectares,
        @DecimalMin("0.0") Double percentualAgriculturaFamiliar
) {
}
