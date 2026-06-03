package br.com.fiap.orbitagro.service;

import br.com.fiap.orbitagro.domain.entity.Cooperativa;
import br.com.fiap.orbitagro.dto.cooperativa.CooperativaRequest;
import br.com.fiap.orbitagro.dto.cooperativa.CooperativaResponse;
import br.com.fiap.orbitagro.exception.BusinessException;
import br.com.fiap.orbitagro.exception.ResourceNotFoundException;
import br.com.fiap.orbitagro.mapper.OrbitAgroMapper;
import br.com.fiap.orbitagro.repository.CooperativaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CooperativaService {
    private final CooperativaRepository repository;

    public CooperativaService(CooperativaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CooperativaResponse criar(CooperativaRequest request) {
        repository.findByCnpj(request.cnpj()).ifPresent(c -> {
            throw new BusinessException("Já existe cooperativa cadastrada com o CNPJ informado.");
        });
        Cooperativa cooperativa = new Cooperativa(
                request.nome(), request.cnpj(), request.email(), request.telefone(),
                OrbitAgroMapper.toEndereco(request.endereco())
        );
        return OrbitAgroMapper.toResponse(repository.save(cooperativa));
    }

    @Transactional(readOnly = true)
    public Cooperativa buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cooperativa não encontrada."));
    }

    @Transactional(readOnly = true)
    public Page<CooperativaResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(OrbitAgroMapper::toResponse);
    }
}
