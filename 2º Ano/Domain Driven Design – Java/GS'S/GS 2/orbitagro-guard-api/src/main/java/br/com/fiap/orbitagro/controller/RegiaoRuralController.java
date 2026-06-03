package br.com.fiap.orbitagro.controller;

import br.com.fiap.orbitagro.dto.regiao.RegiaoRuralRequest;
import br.com.fiap.orbitagro.dto.regiao.RegiaoRuralResponse;
import br.com.fiap.orbitagro.service.RegiaoRuralService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/regioes")
public class RegiaoRuralController {
    private final RegiaoRuralService service;

    public RegiaoRuralController(RegiaoRuralService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegiaoRuralResponse criar(@RequestBody @Valid RegiaoRuralRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public Page<RegiaoRuralResponse> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public RegiaoRuralResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
