package br.com.fiap.orbitagro.service;

import br.com.fiap.orbitagro.domain.entity.AlertaAgroclimatico;
import br.com.fiap.orbitagro.domain.entity.DadoOrbital;
import br.com.fiap.orbitagro.domain.entity.EquipeResposta;
import br.com.fiap.orbitagro.domain.enums.NivelRisco;
import br.com.fiap.orbitagro.domain.enums.StatusAlerta;
import br.com.fiap.orbitagro.dto.alerta.AlertaAgroclimaticoResponse;
import br.com.fiap.orbitagro.dto.alerta.GerarAlertaRequest;
import br.com.fiap.orbitagro.exception.BusinessException;
import br.com.fiap.orbitagro.exception.ResourceNotFoundException;
import br.com.fiap.orbitagro.mapper.OrbitAgroMapper;
import br.com.fiap.orbitagro.repository.AlertaAgroclimaticoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AlertaAgroclimaticoService {
    private final AlertaAgroclimaticoRepository repository;
    private final DadoOrbitalService dadoOrbitalService;
    private final EquipeRespostaService equipeRespostaService;
    private final AlertaDomainService alertaDomainService;

    public AlertaAgroclimaticoService(AlertaAgroclimaticoRepository repository,
                                      DadoOrbitalService dadoOrbitalService,
                                      EquipeRespostaService equipeRespostaService,
                                      AlertaDomainService alertaDomainService) {
        this.repository = repository;
        this.dadoOrbitalService = dadoOrbitalService;
        this.equipeRespostaService = equipeRespostaService;
        this.alertaDomainService = alertaDomainService;
    }

    @Transactional
    public AlertaAgroclimaticoResponse gerarAlerta(GerarAlertaRequest request) {
        DadoOrbital dado = dadoOrbitalService.buscarEntidade(request.dadoOrbitalId());
        repository.findFirstByRegiaoRuralIdAndTipoRiscoAndStatusAndDataGeracaoAfter(
                dado.getRegiaoRural().getId(),
                request.tipoRisco(),
                StatusAlerta.ABERTO,
                LocalDateTime.now().minusHours(24)
        ).ifPresent(alerta -> {
            throw new BusinessException("Já existe alerta aberto para esta região e tipo de risco nas últimas 24 horas.");
        });

        AlertaDomainService.ResultadoClassificacao classificacao = alertaDomainService.classificar(dado, request.tipoRisco());
        if (classificacao.nivelRisco() == NivelRisco.BAIXO) {
            throw new BusinessException("Risco calculado como BAIXO. Alerta operacional não será aberto.");
        }

        AlertaAgroclimatico alerta = new AlertaAgroclimatico(
                gerarCodigo(dado.getRegiaoRural().getUf()),
                request.tipoRisco(),
                classificacao.nivelRisco(),
                classificacao.prioridadeScore(),
                request.descricao(),
                dado.getRegiaoRural(),
                dado
        );
        return OrbitAgroMapper.toResponse(repository.save(alerta));
    }

    @Transactional
    public AlertaAgroclimaticoResponse alocarEquipe(Long alertaId, Long equipeId) {
        AlertaAgroclimatico alerta = buscarEntidade(alertaId);
        EquipeResposta equipe = equipeRespostaService.buscarEntidade(equipeId);
        alerta.alocarEquipe(equipe);
        return OrbitAgroMapper.toResponse(repository.save(alerta));
    }

    @Transactional
    public AlertaAgroclimaticoResponse atualizarStatus(Long alertaId, StatusAlerta novoStatus) {
        AlertaAgroclimatico alerta = buscarEntidade(alertaId);
        alerta.alterarStatus(novoStatus);
        return OrbitAgroMapper.toResponse(repository.save(alerta));
    }

    @Transactional(readOnly = true)
    public AlertaAgroclimatico buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta agroclimático não encontrado."));
    }

    @Transactional(readOnly = true)
    public AlertaAgroclimaticoResponse buscarPorId(Long id) {
        return OrbitAgroMapper.toResponse(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public Page<AlertaAgroclimaticoResponse> listar(StatusAlerta status, NivelRisco nivel, Pageable pageable) {
        if (status != null) {
            return repository.findByStatus(status, pageable).map(OrbitAgroMapper::toResponse);
        }
        if (nivel != null) {
            return repository.findByNivelRisco(nivel, pageable).map(OrbitAgroMapper::toResponse);
        }
        return repository.findAll(pageable).map(OrbitAgroMapper::toResponse);
    }

    private String gerarCodigo(String uf) {
        return "OAG-" + uf.toUpperCase() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
