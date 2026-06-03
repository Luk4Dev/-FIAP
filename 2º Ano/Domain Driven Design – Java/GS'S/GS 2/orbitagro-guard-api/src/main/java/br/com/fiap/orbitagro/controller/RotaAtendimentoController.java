package br.com.fiap.orbitagro.controller;

import br.com.fiap.orbitagro.dto.rota.RotaAtendimentoRequest;
import br.com.fiap.orbitagro.dto.rota.RotaAtendimentoResponse;
import br.com.fiap.orbitagro.service.RotaAtendimentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rotas")
public class RotaAtendimentoController {
    private final RotaAtendimentoService service;

    public RotaAtendimentoController(RotaAtendimentoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RotaAtendimentoResponse planejar(@RequestBody @Valid RotaAtendimentoRequest request) {
        return service.planejar(request);
    }

    @GetMapping
    public Page<RotaAtendimentoResponse> listar(@RequestParam(required = false) Long alertaId, Pageable pageable) {
        return service.listar(alertaId, pageable);
    }
}
