package br.com.fiap.orbitagro.controller;

import br.com.fiap.orbitagro.dto.dado.DadoOrbitalRequest;
import br.com.fiap.orbitagro.dto.dado.DadoOrbitalResponse;
import br.com.fiap.orbitagro.service.DadoOrbitalService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dados-orbitais")
public class DadoOrbitalController {
    private final DadoOrbitalService service;

    public DadoOrbitalController(DadoOrbitalService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DadoOrbitalResponse registrar(@RequestBody @Valid DadoOrbitalRequest request) {
        return service.registrar(request);
    }

    @GetMapping
    public Page<DadoOrbitalResponse> listar(@RequestParam(required = false) Long regiaoId, Pageable pageable) {
        return service.listar(regiaoId, pageable);
    }
}
