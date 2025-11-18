package br.com.harmoniawork.domain.repositories;

import br.com.harmoniawork.domain.entities.Colaborador;
import java.util.List;
import java.util.Optional;

public interface ColaboradorRepository {
    Colaborador save(Colaborador colaborador);
    Optional<Colaborador> findById(Long id);
    List<Colaborador> findAll();
    void update(Colaborador colaborador);
}
