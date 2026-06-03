package br.com.fiap.orbitagro.dto.regiao;

import br.com.fiap.orbitagro.domain.enums.NivelConectividade;
import br.com.fiap.orbitagro.dto.common.AreaMonitoradaDTO;
import br.com.fiap.orbitagro.dto.common.CoordenadaDTO;

public record RegiaoRuralResponse(
        Long id,
        String nome,
        String municipio,
        String uf,
        CoordenadaDTO coordenada,
        AreaMonitoradaDTO areaMonitorada,
        NivelConectividade nivelConectividade
) {
}
