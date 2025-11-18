package br.com.harmoniawork.application.usecases;

import br.com.harmoniawork.domain.entities.Colaborador;
import br.com.harmoniawork.domain.repositories.ColaboradorRepository;
import br.com.harmoniawork.domain.services.IARecomendacoesService;

import java.util.List;
import java.util.Optional;

public class GerarRecomendacoesUseCase {
    private final ColaboradorRepository repo;
    private final IARecomendacoesService ia;

    public GerarRecomendacoesUseCase(ColaboradorRepository repo, IARecomendacoesService ia) {
        this.repo = repo;
        this.ia = ia;
    }

    public List<String> execute(Long colaboradorId) {
        Optional<Colaborador> oc = repo.findById(colaboradorId);
        if (!oc.isPresent()) return List.of("Colaborador não encontrado.");
        return ia.gerarRecomendacoes(oc.get());
    }
}
