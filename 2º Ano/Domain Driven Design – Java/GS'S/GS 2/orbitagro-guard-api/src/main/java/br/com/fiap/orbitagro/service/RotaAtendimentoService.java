package br.com.fiap.orbitagro.service;

import br.com.fiap.orbitagro.domain.entity.AlertaAgroclimatico;
import br.com.fiap.orbitagro.domain.entity.EquipeResposta;
import br.com.fiap.orbitagro.domain.entity.RotaAtendimento;
import br.com.fiap.orbitagro.domain.enums.StatusAlerta;
import br.com.fiap.orbitagro.dto.rota.RotaAtendimentoRequest;
import br.com.fiap.orbitagro.dto.rota.RotaAtendimentoResponse;
import br.com.fiap.orbitagro.exception.BusinessException;
import br.com.fiap.orbitagro.mapper.OrbitAgroMapper;
import br.com.fiap.orbitagro.repository.RotaAtendimentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RotaAtendimentoService {
    private static final double CUSTO_MEDIO_POR_KM = 4.80;

    private final RotaAtendimentoRepository repository;
    private final AlertaAgroclimaticoService alertaService;
    private final EquipeRespostaService equipeService;

    public RotaAtendimentoService(RotaAtendimentoRepository repository,
                                  AlertaAgroclimaticoService alertaService,
                                  EquipeRespostaService equipeService) {
        this.repository = repository;
        this.alertaService = alertaService;
        this.equipeService = equipeService;
    }

    @Transactional
    public RotaAtendimentoResponse planejar(RotaAtendimentoRequest request) {
        AlertaAgroclimatico alerta = alertaService.buscarEntidade(request.alertaId());
        EquipeResposta equipe = equipeService.buscarEntidade(request.equipeId());

        if (alerta.getStatus() != StatusAlerta.EM_ATENDIMENTO) {
            throw new BusinessException("Rotas só podem ser planejadas para alertas em atendimento.");
        }
        if (!alerta.getEquipesResponsaveis().contains(equipe)) {
            throw new BusinessException("A equipe informada precisa estar alocada ao alerta antes do planejamento da rota.");
        }

        double custo = request.distanciaKm() * CUSTO_MEDIO_POR_KM;
        RotaAtendimento rota = new RotaAtendimento(
                request.origem(),
                request.destino(),
                OrbitAgroMapper.toCoordenada(request.coordenadaDestino()),
                request.distanciaKm(),
                request.tempoEstimadoMinutos(),
                custo,
                alerta,
                equipe
        );
        alerta.adicionarRota(rota);
        return OrbitAgroMapper.toResponse(repository.save(rota));
    }

    @Transactional(readOnly = true)
    public Page<RotaAtendimentoResponse> listar(Long alertaId, Pageable pageable) {
        if (alertaId != null) {
            return repository.findByAlertaId(alertaId, pageable).map(OrbitAgroMapper::toResponse);
        }
        return repository.findAll(pageable).map(OrbitAgroMapper::toResponse);
    }
}
