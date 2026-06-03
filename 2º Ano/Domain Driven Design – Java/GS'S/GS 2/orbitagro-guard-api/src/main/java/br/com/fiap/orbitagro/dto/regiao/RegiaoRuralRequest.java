package br.com.fiap.orbitagro.dto.regiao;

import br.com.fiap.orbitagro.domain.enums.NivelConectividade;
import br.com.fiap.orbitagro.dto.common.AreaMonitoradaDTO;
import br.com.fiap.orbitagro.dto.common.CoordenadaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegiaoRuralRequest(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Size(max = 80) String municipio,
        @NotBlank @Size(min = 2, max = 2) String uf,
        @Valid @NotNull CoordenadaDTO coordenada,
        @Valid @NotNull AreaMonitoradaDTO areaMonitorada,
        @NotNull NivelConectividade nivelConectividade
) {
}
