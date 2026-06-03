package br.com.fiap.orbitagro.dto.alerta;

import br.com.fiap.orbitagro.domain.enums.TipoRisco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GerarAlertaRequest(
        @NotNull Long dadoOrbitalId,
        @NotNull TipoRisco tipoRisco,
        @NotBlank @Size(max = 500) String descricao
) {
}
