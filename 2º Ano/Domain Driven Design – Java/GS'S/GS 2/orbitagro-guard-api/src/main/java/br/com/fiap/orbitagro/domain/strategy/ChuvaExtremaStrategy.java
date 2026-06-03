package br.com.fiap.orbitagro.domain.strategy;

import br.com.fiap.orbitagro.domain.entity.DadoOrbital;
import br.com.fiap.orbitagro.domain.enums.TipoRisco;
import org.springframework.stereotype.Component;

@Component
public class ChuvaExtremaStrategy implements RiscoAgroclimaticoStrategy {
    @Override
    public TipoRisco tipoRisco() { return TipoRisco.CHUVA_EXTREMA; }

    @Override
    public int calcularScore(DadoOrbital dado) {
        double score = 0;
        score += dado.getPrecipitacaoMm() >= 90 ? 70 : dado.getPrecipitacaoMm() >= 50 ? 45 : 10;
        score += dado.getUmidadeSoloPercentual() >= 85 ? 20 : dado.getUmidadeSoloPercentual() >= 70 ? 10 : 0;
        return Math.max(0, Math.min(100, (int) Math.round(score)));
    }
}
