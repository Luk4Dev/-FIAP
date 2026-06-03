package br.com.fiap.orbitagro.controller;

import br.com.fiap.orbitagro.dto.equipe.EquipeRespostaRequest;
import br.com.fiap.orbitagro.dto.equipe.EquipeRespostaResponse;
import br.com.fiap.orbitagro.service.EquipeRespostaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipes")
public class EquipeRespostaController {
    private final EquipeRespostaService service;

    public EquipeRespostaController(EquipeRespostaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipeRespostaResponse criar(@RequestBody @Valid EquipeRespostaRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public Page<EquipeRespostaResponse> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/disponiveis")
    public Page<EquipeRespostaResponse> listarDisponiveis(Pageable pageable) {
        return service.listarDisponiveis(pageable);
    }
}
