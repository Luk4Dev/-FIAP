package br.com.harmoniawork.domain.services;

import br.com.harmoniawork.domain.entities.Colaborador;
import br.com.harmoniawork.domain.entities.TipoAtividade;

import java.util.ArrayList;
import java.util.List;

public class IARecomendacoesService {

    private CalculoIndicadoresService calculoService = new CalculoIndicadoresService();

    public List<String> gerarRecomendacoes(Colaborador c) {
        List<String> recs = new ArrayList<>();

        int estresse = c.getNivelEstresse();
        int integracao = c.getIndiceIntegracao();
        int bemEstarScore = calculoService.calcularScoreBemEstar(c);

        if (estresse >= 75) {
            recs.add("Alto estresse detectado — sugerir meditação de 10 minutos.");
            recs.add("Sugerir pausa curta (5-10min) a cada 50 minutos de trabalho.");
        } else if (estresse >= 50) {
            recs.add("Estresse moderado — fazer pausa de 5 minutos e alongamento.");
        } else {
            recs.add("Nível de estresse aceitável — manter rotina, fazer pausas preventivas.");
        }

        if (!c.isErgonomiaAjustada()) {
            recs.add("Ajuste ergonômico recomendado — verificar cadeira e altura da tela.");
        }

        if (integracao < 50) {
            recs.add("Baixa integração — sugerir participação em reunião híbrida (Tipo: HIBRIDA).");
        }

        if (bemEstarScore < 50) {
            recs.add("Score de bem-estar baixo — propor atividades de bem-estar e revisão do ambiente físico.");
        }

        // sugestão prática
        recs.add("Sugestão automática: registrar uma atividade do tipo " + TipoAtividade.PAUSA + " ou " + TipoAtividade.MEDITACAO);

        return recs;
    }
}
