package br.com.fiap.orbitagro.dto.rota;

import br.com.fiap.orbitagro.domain.enums.StatusRota;
import br.com.fiap.orbitagro.dto.common.CoordenadaDTO;

public record RotaAtendimentoResponse(
        Long id,
        Long alertaId,
        Long equipeId,
        String origem,
        String destino,
        CoordenadaDTO coordenadaDestino,
        Double distanciaKm,
        Integer tempoEstimadoMinutos,
        Double custoOperacionalEstimado,
        StatusRota status
) {
}
