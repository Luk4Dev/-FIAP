package br.com.fiap.orbitagro.dto.alerta;

import br.com.fiap.orbitagro.domain.enums.StatusAlerta;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusAlertaRequest(
        @NotNull StatusAlerta status
) {
}
