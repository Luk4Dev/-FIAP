package br.com.harmoniawork.domain.entities;

import br.com.harmoniawork.domain.valueobjects.IndicadoresDeBemEstar;

public class Colaborador {
    private Long id;
    private String nome;
    private String modoDeTrabalho; // "REMOTO", "PRESENCIAL", "HÍBRIDO"
    private int nivelEstresse; // 0-100
    private int indiceIntegracao; // 0-100
    private boolean ergonomiaAjustada;
    private IndicadoresDeBemEstar indicadores;

    public Colaborador(Long id, String nome, String modoDeTrabalho) {
        this.id = id;
        this.nome = nome;
        this.modoDeTrabalho = modoDeTrabalho;
        this.nivelEstresse = 30; // valor inicial default
        this.indiceIntegracao = modoDeTrabalho.equalsIgnoreCase("REMOTO") ? 40 : 70;
        this.ergonomiaAjustada = false;
        this.indicadores = new IndicadoresDeBemEstar(30, 60, 70, 70, 70);
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getModoDeTrabalho() { return modoDeTrabalho; }
    public int getNivelEstresse() { return nivelEstresse; }
    public int getIndiceIntegracao() { return indiceIntegracao; }
    public boolean isErgonomiaAjustada() { return ergonomiaAjustada; }
    public IndicadoresDeBemEstar getIndicadores() { return indicadores; }

    // Regra de negócio: aplicar atividade altera estresse e integração
    public void aplicarAtividade(Atividade atividade) {
        int novoEstresse = this.nivelEstresse + atividade.getImpactoEstresse();
        this.nivelEstresse = clamp(novoEstresse);

        int novaIntegracao = this.indiceIntegracao + atividade.getImpactoIntegracao();
        this.indiceIntegracao = clamp(novaIntegracao);

        // efeitos secundários simples nos indicadores
        if (atividade.getTipo() == TipoAtividade.MEDITACAO || atividade.getTipo() == TipoAtividade.PAUSA) {
            this.indicadores.reduzirFadiga(Math.abs(atividade.getImpactoEstresse()));
        }
        if (atividade.getTipo() == TipoAtividade.TREINAMENTO || atividade.getTipo() == TipoAtividade.HIBRIDA) {
            this.indicadores.melhorarErgonomia(2);
        }

        // remotos tendem a perder integração com o tempo (simulação leve)
        if (modoDeTrabalho.equalsIgnoreCase("REMOTO")) {
            this.indiceIntegracao = clamp(this.indiceIntegracao - 1);
        }
    }

    public void ajustarErgonomia() {
        this.ergonomiaAjustada = true;
        this.indicadores.melhorarErgonomia(15);
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }

    @Override
    public String toString() {
        return String.format("%s (id:%d) - %s | Estresse:%d Integração:%d Ergonomia:%s",
                nome, id, modoDeTrabalho, nivelEstresse, indiceIntegracao, ergonomiaAjustada ? "OK" : "NÃO");
    }
}
