package br.com.harmoniawork.infrastructure.persistence;

import br.com.harmoniawork.domain.entities.SessaoDeTrabalho;
import br.com.harmoniawork.domain.repositories.SessaoRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemorySessaoRepository implements SessaoRepository {
    private final Map<Long, SessaoDeTrabalho> store = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    @Override
    public SessaoDeTrabalho save(SessaoDeTrabalho sessao) {
        Long id = seq.getAndIncrement();
        SessaoDeTrabalho s = new SessaoDeTrabalho(id, sessao.getColaboradorId());
        sessao.getAtividades().forEach(s::registrarAtividade);
        store.put(id, s);
        return s;
    }

    @Override
    public Optional<SessaoDeTrabalho> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<SessaoDeTrabalho> findAll() {
        return new ArrayList<>(store.values());
    }
}
