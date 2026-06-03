package br.com.fiap.orbitagro.domain.strategy;

import br.com.fiap.orbitagro.domain.entity.DadoOrbital;
import br.com.fiap.orbitagro.domain.enums.TipoRisco;
import org.springframework.stereotype.Component;

@Component
public class QueimadaStrategy implements RiscoAgroclimaticoStrategy {
    @Override
    public TipoRisco tipoRisco() { return TipoRisco.QUEIMADA; }

    @Override
    public int calcularScore(DadoOrbital dado) {
        double score = 0;
        score += dado.getTemperaturaSuperficie() >= 36 ? 32 : dado.getTemperaturaSuperficie() >= 32 ? 18 : 6;
        score += dado.getPrecipitacaoMm() <= 1 ? 25 : dado.getPrecipitacaoMm() <= 5 ? 12 : 0;
        score += dado.getUmidadeSoloPercentual() <= 20 ? 22 : dado.getUmidadeSoloPercentual() <= 35 ? 10 : 0;
        score += dado.getIndiceVegetacaoNdvi() < 0.25 ? 15 : dado.getIndiceVegetacaoNdvi() < 0.40 ? 7 : 0;
        return Math.max(0, Math.min(100, (int) Math.round(score)));
    }
}
