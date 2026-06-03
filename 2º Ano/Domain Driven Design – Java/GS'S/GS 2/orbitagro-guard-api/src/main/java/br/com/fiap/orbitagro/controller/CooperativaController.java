package br.com.fiap.orbitagro.controller;

import br.com.fiap.orbitagro.dto.cooperativa.CooperativaRequest;
import br.com.fiap.orbitagro.dto.cooperativa.CooperativaResponse;
import br.com.fiap.orbitagro.service.CooperativaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cooperativas")
public class CooperativaController {
    private final CooperativaService service;

    public CooperativaController(CooperativaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CooperativaResponse criar(@RequestBody @Valid CooperativaRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public Page<CooperativaResponse> listar(Pageable pageable) {
        return service.listar(pageable);
    }
}
