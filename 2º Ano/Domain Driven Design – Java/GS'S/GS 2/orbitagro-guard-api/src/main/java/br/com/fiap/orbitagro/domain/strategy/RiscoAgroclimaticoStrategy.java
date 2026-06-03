package br.com.fiap.orbitagro.domain.strategy;

import br.com.fiap.orbitagro.domain.entity.DadoOrbital;
import br.com.fiap.orbitagro.domain.enums.TipoRisco;

public interface RiscoAgroclimaticoStrategy {
    TipoRisco tipoRisco();
    int calcularScore(DadoOrbital dadoOrbital);
}
