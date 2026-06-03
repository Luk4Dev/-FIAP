package br.com.fiap.orbitagro.controller;

import br.com.fiap.orbitagro.dto.produtor.ProdutorRequest;
import br.com.fiap.orbitagro.dto.produtor.ProdutorResponse;
import br.com.fiap.orbitagro.service.ProdutorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtores")
public class ProdutorController {
    private final ProdutorService service;

    public ProdutorController(ProdutorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutorResponse criar(@RequestBody @Valid ProdutorRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public Page<ProdutorResponse> listar(@RequestParam(required = false) Long regiaoId, Pageable pageable) {
        return service.listar(regiaoId, pageable);
    }
}
