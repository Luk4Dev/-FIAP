package br.com.harmoniawork.domain.repositories;

import br.com.harmoniawork.domain.entities.Atividade;
import java.util.List;
import java.util.Optional;

public interface AtividadeRepository {
    Atividade save(Atividade atividade);
    Optional<Atividade> findById(Long id);
    List<Atividade> findAll();
}
