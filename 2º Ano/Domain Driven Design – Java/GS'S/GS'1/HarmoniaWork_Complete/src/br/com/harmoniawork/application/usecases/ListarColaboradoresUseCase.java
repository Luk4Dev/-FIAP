package br.com.harmoniawork.application.usecases;

import br.com.harmoniawork.domain.entities.Colaborador;
import br.com.harmoniawork.domain.repositories.ColaboradorRepository;
import java.util.List;

public class ListarColaboradoresUseCase {
    private final ColaboradorRepository repo;

    public ListarColaboradoresUseCase(ColaboradorRepository repo) {
        this.repo = repo;
    }

    public List<Colaborador> execute() {
        return repo.findAll();
    }
}
