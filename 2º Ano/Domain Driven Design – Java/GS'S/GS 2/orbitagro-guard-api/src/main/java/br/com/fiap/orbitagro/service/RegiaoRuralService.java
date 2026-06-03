package br.com.fiap.orbitagro.service;

import br.com.fiap.orbitagro.domain.entity.RegiaoRural;
import br.com.fiap.orbitagro.dto.regiao.RegiaoRuralRequest;
import br.com.fiap.orbitagro.dto.regiao.RegiaoRuralResponse;
import br.com.fiap.orbitagro.exception.ResourceNotFoundException;
import br.com.fiap.orbitagro.mapper.OrbitAgroMapper;
import br.com.fiap.orbitagro.repository.RegiaoRuralRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegiaoRuralService {
    private final RegiaoRuralRepository repository;

    public RegiaoRuralService(RegiaoRuralRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public RegiaoRuralResponse criar(RegiaoRuralRequest request) {
        RegiaoRural regiao = new RegiaoRural(
                request.nome(),
                request.municipio(),
                request.uf().toUpperCase(),
                OrbitAgroMapper.toCoordenada(request.coordenada()),
                OrbitAgroMapper.toAreaMonitorada(request.areaMonitorada()),
                request.nivelConectividade()
        );
        return OrbitAgroMapper.toResponse(repository.save(regiao));
    }

    @Transactional(readOnly = true)
    public Page<RegiaoRuralResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(OrbitAgroMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public RegiaoRural buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Região rural não encontrada."));
    }

    @Transactional(readOnly = true)
    public RegiaoRuralResponse buscarPorId(Long id) {
        return OrbitAgroMapper.toResponse(buscarEntidade(id));
    }
}
