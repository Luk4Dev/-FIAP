package br.com.harmoniawork.infrastructure.persistence;

import br.com.harmoniawork.domain.entities.Atividade;
import br.com.harmoniawork.domain.repositories.AtividadeRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryAtividadeRepository implements AtividadeRepository {
    private final Map<Long, Atividade> store = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    @Override
    public Atividade save(Atividade atividade) {
        Long id = seq.getAndIncrement();
        Atividade a = new Atividade(id, atividade.getTipo(), atividade.getImpactoEstresse(), atividade.getImpactoIntegracao());
        store.put(id, a);
        return a;
    }

    @Override
    public Optional<Atividade> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Atividade> findAll() {
        return new ArrayList<>(store.values());
    }
}
