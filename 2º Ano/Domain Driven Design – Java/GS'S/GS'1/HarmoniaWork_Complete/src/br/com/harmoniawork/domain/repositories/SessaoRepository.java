package br.com.harmoniawork.domain.repositories;

import br.com.harmoniawork.domain.entities.SessaoDeTrabalho;
import java.util.Optional;
import java.util.List;

public interface SessaoRepository {
    SessaoDeTrabalho save(SessaoDeTrabalho sessao);
    Optional<SessaoDeTrabalho> findById(Long id);
    List<SessaoDeTrabalho> findAll();
}
