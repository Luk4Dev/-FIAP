package br.com.harmoniawork.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessaoDeTrabalho {
    private Long id;
    private Long colaboradorId;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private List<Atividade> atividades;

    public SessaoDeTrabalho(Long id, Long colaboradorId) {
        this.id = id;
        this.colaboradorId = colaboradorId;
        this.inicio = LocalDateTime.now();
        this.atividades = new ArrayList<>();
    }

    public Long getId() { return id; }
    public Long getColaboradorId() { return colaboradorId; }
    public java.time.LocalDateTime getInicio() { return inicio; }
    public java.time.LocalDateTime getFim() { return fim; }
    public List<Atividade> getAtividades() { return atividades; }

    public void registrarAtividade(Atividade atividade) {
        this.atividades.add(atividade);
    }

    public void encerrar() {
        this.fim = LocalDateTime.now();
    }
}
