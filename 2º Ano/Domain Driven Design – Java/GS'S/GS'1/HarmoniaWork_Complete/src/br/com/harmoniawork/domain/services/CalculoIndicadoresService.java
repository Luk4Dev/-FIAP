package br.com.harmoniawork.domain.services;

import br.com.harmoniawork.domain.entities.Colaborador;
import br.com.harmoniawork.domain.valueobjects.IndicadoresDeBemEstar;

public class CalculoIndicadoresService {

    // Calcula um "score" simples de bem-estar (0-100)
    public int calcularScoreBemEstar(Colaborador c) {
        IndicadoresDeBemEstar i = c.getIndicadores();
        // fórmula simples: melhores indicadores reduzem estresse e aumentam score
        int score = 100 - i.getFadiga(); // base
        score += i.getErgonomia() / 2;
        score += i.getLuz() / 10;
        score = clamp(score);
        return score;
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }
}
