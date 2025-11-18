package br.com.harmoniawork.application.usecases;

import br.com.harmoniawork.domain.entities.Colaborador;
import br.com.harmoniawork.domain.repositories.ColaboradorRepository;

import java.util.List;

public class CadastrarColaboradorUseCase {
    private final ColaboradorRepository repo;

    public CadastrarColaboradorUseCase(ColaboradorRepository repo) {
        this.repo = repo;
    }

    public Colaborador execute(String nome, String modo) {
        Colaborador c = new Colaborador(null, nome, modo);
        return repo.save(c);
    }

    public List<Colaborador> listar() {
        return repo.findAll();
    }
}
