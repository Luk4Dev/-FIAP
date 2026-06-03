package br.com.fiap.orbitagro.service;

import br.com.fiap.orbitagro.domain.entity.DadoOrbital;
import br.com.fiap.orbitagro.domain.entity.RegiaoRural;
import br.com.fiap.orbitagro.dto.dado.DadoOrbitalRequest;
import br.com.fiap.orbitagro.dto.dado.DadoOrbitalResponse;
import br.com.fiap.orbitagro.exception.ResourceNotFoundException;
import br.com.fiap.orbitagro.mapper.OrbitAgroMapper;
import br.com.fiap.orbitagro.repository.DadoOrbitalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DadoOrbitalService {
    private final DadoOrbitalRepository repository;
    private final RegiaoRuralService regiaoRuralService;

    public DadoOrbitalService(DadoOrbitalRepository repository, RegiaoRuralService regiaoRuralService) {
        this.repository = repository;
        this.regiaoRuralService = regiaoRuralService;
    }

    @Transactional
    public DadoOrbitalResponse registrar(DadoOrbitalRequest request) {
        RegiaoRural regiao = regiaoRuralService.buscarEntidade(request.regiaoRuralId());
        DadoOrbital dado = new DadoOrbital(
                request.dataColeta(), request.fonte(), request.indiceVegetacaoNdvi(),
                request.temperaturaSuperficie(), request.precipitacaoMm(),
                request.umidadeSoloPercentual(), request.indiceRiscoSeca(), regiao
        );
        return OrbitAgroMapper.toResponse(repository.save(dado));
    }

    @Transactional(readOnly = true)
    public DadoOrbital buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dado orbital não encontrado."));
    }

    @Transactional(readOnly = true)
    public Page<DadoOrbitalResponse> listar(Long regiaoId, Pageable pageable) {
        if (regiaoId != null) {
            return repository.findByRegiaoRuralId(regiaoId, pageable).map(OrbitAgroMapper::toResponse);
        }
        return repository.findAll(pageable).map(OrbitAgroMapper::toResponse);
    }
}
