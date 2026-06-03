package br.com.fiap.orbitagro.dto.alerta;

import br.com.fiap.orbitagro.domain.enums.NivelRisco;
import br.com.fiap.orbitagro.domain.enums.StatusAlerta;
import br.com.fiap.orbitagro.domain.enums.TipoRisco;

import java.time.LocalDateTime;
import java.util.List;

public record AlertaAgroclimaticoResponse(
        Long id,
        String codigo,
        TipoRisco tipoRisco,
        NivelRisco nivelRisco,
        StatusAlerta status,
        Integer prioridadeScore,
        String descricao,
        LocalDateTime dataGeracao,
        Long regiaoRuralId,
        String regiaoRuralNome,
        Long dadoOrbitalId,
        List<String> equipesResponsaveis
) {
}
