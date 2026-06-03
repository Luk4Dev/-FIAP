package br.com.fiap.orbitagro.domain.strategy;

import br.com.fiap.orbitagro.domain.entity.DadoOrbital;
import br.com.fiap.orbitagro.domain.enums.TipoRisco;
import org.springframework.stereotype.Component;

@Component
public class GeadaStrategy implements RiscoAgroclimaticoStrategy {
    @Override
    public TipoRisco tipoRisco() { return TipoRisco.GEADA; }

    @Override
    public int calcularScore(DadoOrbital dado) {
        double score = 0;
        score += dado.getTemperaturaSuperficie() <= 2 ? 70 : dado.getTemperaturaSuperficie() <= 5 ? 45 : 15;
        score += dado.getUmidadeSoloPercentual() >= 70 ? 15 : 5;
        score += dado.getIndiceVegetacaoNdvi() > 0.45 ? 10 : 0;
        return Math.max(0, Math.min(100, (int) Math.round(score)));
    }
}
