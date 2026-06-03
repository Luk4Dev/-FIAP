package br.com.fiap.orbitagro.controller;

import br.com.fiap.orbitagro.domain.enums.NivelRisco;
import br.com.fiap.orbitagro.domain.enums.StatusAlerta;
import br.com.fiap.orbitagro.dto.alerta.AlertaAgroclimaticoResponse;
import br.com.fiap.orbitagro.dto.alerta.AtualizarStatusAlertaRequest;
import br.com.fiap.orbitagro.dto.alerta.GerarAlertaRequest;
import br.com.fiap.orbitagro.service.AlertaAgroclimaticoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alertas")
public class AlertaAgroclimaticoController {
    private final AlertaAgroclimaticoService service;

    public AlertaAgroclimaticoController(AlertaAgroclimaticoService service) {
        this.service = service;
    }

    @PostMapping("/gerar")
    @ResponseStatus(HttpStatus.CREATED)
    public AlertaAgroclimaticoResponse gerarAlerta(@RequestBody @Valid GerarAlertaRequest request) {
        return service.gerarAlerta(request);
    }

    @GetMapping
    public Page<AlertaAgroclimaticoResponse> listar(@RequestParam(required = false) StatusAlerta status,
                                                    @RequestParam(required = false) NivelRisco nivel,
                                                    Pageable pageable) {
        return service.listar(status, nivel, pageable);
    }

    @GetMapping("/{id}")
    public AlertaAgroclimaticoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PatchMapping("/{alertaId}/equipes/{equipeId}")
    public AlertaAgroclimaticoResponse alocarEquipe(@PathVariable Long alertaId, @PathVariable Long equipeId) {
        return service.alocarEquipe(alertaId, equipeId);
    }

    @PatchMapping("/{alertaId}/status")
    public AlertaAgroclimaticoResponse atualizarStatus(@PathVariable Long alertaId,
                                                       @RequestBody @Valid AtualizarStatusAlertaRequest request) {
        return service.atualizarStatus(alertaId, request.status());
    }
}
