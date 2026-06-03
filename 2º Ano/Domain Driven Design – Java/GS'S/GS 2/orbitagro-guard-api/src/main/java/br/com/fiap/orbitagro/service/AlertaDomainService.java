package br.com.fiap.orbitagro.service;

import br.com.fiap.orbitagro.domain.entity.DadoOrbital;
import br.com.fiap.orbitagro.domain.enums.NivelRisco;
import br.com.fiap.orbitagro.domain.enums.TipoRisco;
import br.com.fiap.orbitagro.domain.strategy.RiscoAgroclimaticoStrategy;
import br.com.fiap.orbitagro.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class AlertaDomainService {
    private final Map<TipoRisco, RiscoAgroclimaticoStrategy> strategies = new EnumMap<>(TipoRisco.class);

    public AlertaDomainService(List<RiscoAgroclimaticoStrategy> strategies) {
        strategies.forEach(strategy -> this.strategies.put(strategy.tipoRisco(), strategy));
    }

    public ResultadoClassificacao classificar(DadoOrbital dadoOrbital, TipoRisco tipoRisco) {
        RiscoAgroclimaticoStrategy strategy = strategies.get(tipoRisco);
        if (strategy == null) {
            throw new BusinessException("Não há estratégia de cálculo para o tipo de risco informado.");
        }
        int score = strategy.calcularScore(dadoOrbital);
        return new ResultadoClassificacao(score, NivelRisco.porScore(score));
    }

    public record ResultadoClassificacao(int prioridadeScore, NivelRisco nivelRisco) {
    }
}
