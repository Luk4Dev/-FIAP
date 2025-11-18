package br.com.harmoniawork.infrastructure.persistence;

import br.com.harmoniawork.domain.entities.Colaborador;
import br.com.harmoniawork.domain.repositories.ColaboradorRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryColaboradorRepository implements ColaboradorRepository {
    private final Map<Long, Colaborador> store = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    @Override
    public Colaborador save(Colaborador colaborador) {
        Long id = seq.getAndIncrement();
        Colaborador novo = new Colaborador(id, colaborador.getNome(), colaborador.getModoDeTrabalho());
        store.put(id, novo);
        return novo;
    }

    @Override
    public Optional<Colaborador> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Colaborador> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Colaborador colaborador) {
        store.put(colaborador.getId(), colaborador);
    }
}
