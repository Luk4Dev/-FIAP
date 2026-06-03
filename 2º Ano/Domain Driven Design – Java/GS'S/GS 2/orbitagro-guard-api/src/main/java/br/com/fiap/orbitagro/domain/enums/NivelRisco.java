package br.com.fiap.orbitagro.domain.enums;

public enum NivelRisco {
    BAIXO,
    MEDIO,
    ALTO,
    CRITICO;

    public static NivelRisco porScore(int score) {
        if (score >= 85) return CRITICO;
        if (score >= 65) return ALTO;
        if (score >= 40) return MEDIO;
        return BAIXO;
    }
}
