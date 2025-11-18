package br.com.harmoniawork.domain.entities;

public class Atividade {
    private Long id;
    private TipoAtividade tipo;
    private int impactoEstresse;     // positivo aumenta estresse, negativo reduz
    private int impactoIntegracao;   // positivo aumenta integração

    public Atividade(Long id, TipoAtividade tipo, int impactoEstresse, int impactoIntegracao) {
        this.id = id;
        this.tipo = tipo;
        this.impactoEstresse = impactoEstresse;
        this.impactoIntegracao = impactoIntegracao;
    }

    public Long getId() { return id; }
    public TipoAtividade getTipo() { return tipo; }
    public int getImpactoEstresse() { return impactoEstresse; }
    public int getImpactoIntegracao() { return impactoIntegracao; }

    @Override
    public String toString() {
        return String.format("%s (Estresse %+d, Integração %+d)", tipo, impactoEstresse, impactoIntegracao);
    }

    // Fábrica simples para tipos comuns
    public static Atividade padrao(Long id, TipoAtividade tipo) {
        switch (tipo) {
            case REUNIAO: return new Atividade(id, tipo, +5, +10);
            case FOCO: return new Atividade(id, tipo, +2, -2);
            case PAUSA: return new Atividade(id, tipo, -8, 0);
            case MEDITACAO: return new Atividade(id, tipo, -15, 0);
            case TREINAMENTO: return new Atividade(id, tipo, -2, +8);
            case HIBRIDA: return new Atividade(id, tipo, +1, +15);
            default: return new Atividade(id, tipo, 0, 0);
        }
    }
}
