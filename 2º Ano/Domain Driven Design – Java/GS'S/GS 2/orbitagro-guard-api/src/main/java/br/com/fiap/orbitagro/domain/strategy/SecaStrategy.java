package br.com.fiap.orbitagro.domain.strategy;

import br.com.fiap.orbitagro.domain.entity.DadoOrbital;
import br.com.fiap.orbitagro.domain.enums.TipoRisco;
import org.springframework.stereotype.Component;

@Component
public class SecaStrategy implements RiscoAgroclimaticoStrategy {
    @Override
    public TipoRisco tipoRisco() { return TipoRisco.SECA; }

    @Override
    public int calcularScore(DadoOrbital dado) {
        double score = dado.getIndiceRiscoSeca() * 0.50;
        score += dado.getTemperaturaSuperficie() >= 34 ? 18 : dado.getTemperaturaSuperficie() >= 30 ? 10 : 0;
        score += dado.getPrecipitacaoMm() <= 2 ? 15 : dado.getPrecipitacaoMm() <= 10 ? 8 : 0;
        score += dado.getUmidadeSoloPercentual() <= 25 ? 12 : dado.getUmidadeSoloPercentual() <= 40 ? 6 : 0;
        score += dado.getIndiceVegetacaoNdvi() < 0.30 ? 10 : 0;
        return limitar(score);
    }

    private int limitar(double score) {
        return Math.max(0, Math.min(100, (int) Math.round(score)));
    }
}
