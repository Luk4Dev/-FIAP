package br.com.fiap.orbitagro.service;

import br.com.fiap.orbitagro.domain.entity.Cooperativa;
import br.com.fiap.orbitagro.domain.entity.Produtor;
import br.com.fiap.orbitagro.domain.entity.RegiaoRural;
import br.com.fiap.orbitagro.dto.produtor.ProdutorRequest;
import br.com.fiap.orbitagro.dto.produtor.ProdutorResponse;
import br.com.fiap.orbitagro.mapper.OrbitAgroMapper;
import br.com.fiap.orbitagro.repository.ProdutorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutorService {
    private final ProdutorRepository repository;
    private final RegiaoRuralService regiaoRuralService;
    private final CooperativaService cooperativaService;

    public ProdutorService(ProdutorRepository repository, RegiaoRuralService regiaoRuralService,
                           CooperativaService cooperativaService) {
        this.repository = repository;
        this.regiaoRuralService = regiaoRuralService;
        this.cooperativaService = cooperativaService;
    }

    @Transactional
    public ProdutorResponse criar(ProdutorRequest request) {
        RegiaoRural regiao = regiaoRuralService.buscarEntidade(request.regiaoRuralId());
        Cooperativa cooperativa = request.cooperativaId() == null ? null : cooperativaService.buscarEntidade(request.cooperativaId());
        Produtor produtor = new Produtor(
                request.nome(), request.documento(), request.email(), request.telefone(),
                OrbitAgroMapper.toEndereco(request.endereco()), regiao, cooperativa
        );
        return OrbitAgroMapper.toResponse(repository.save(produtor));
    }

    @Transactional(readOnly = true)
    public Page<ProdutorResponse> listar(Long regiaoId, Pageable pageable) {
        if (regiaoId != null) {
            return repository.findByRegiaoRuralId(regiaoId, pageable).map(OrbitAgroMapper::toResponse);
        }
        return repository.findAll(pageable).map(OrbitAgroMapper::toResponse);
    }
}
