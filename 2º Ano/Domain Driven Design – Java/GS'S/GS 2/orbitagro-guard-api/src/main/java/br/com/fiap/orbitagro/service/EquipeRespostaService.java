package br.com.fiap.orbitagro.service;

import br.com.fiap.orbitagro.domain.entity.EquipeResposta;
import br.com.fiap.orbitagro.dto.equipe.EquipeRespostaRequest;
import br.com.fiap.orbitagro.dto.equipe.EquipeRespostaResponse;
import br.com.fiap.orbitagro.exception.ResourceNotFoundException;
import br.com.fiap.orbitagro.mapper.OrbitAgroMapper;
import br.com.fiap.orbitagro.repository.EquipeRespostaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipeRespostaService {
    private final EquipeRespostaRepository repository;

    public EquipeRespostaService(EquipeRespostaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public EquipeRespostaResponse criar(EquipeRespostaRequest request) {
        EquipeResposta equipe = new EquipeResposta(
                request.nome(), request.tipoEquipe(), request.capacidadeAtendimentoDiaria(), request.telefoneContato()
        );
        return OrbitAgroMapper.toResponse(repository.save(equipe));
    }

    @Transactional(readOnly = true)
    public EquipeResposta buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe de resposta não encontrada."));
    }

    @Transactional(readOnly = true)
    public Page<EquipeRespostaResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(OrbitAgroMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<EquipeRespostaResponse> listarDisponiveis(Pageable pageable) {
        return repository.findByDisponivelTrue(pageable).map(OrbitAgroMapper::toResponse);
    }
}
