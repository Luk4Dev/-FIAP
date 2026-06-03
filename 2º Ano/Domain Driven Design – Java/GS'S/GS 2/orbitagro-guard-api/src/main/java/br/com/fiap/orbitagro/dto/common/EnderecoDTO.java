package br.com.fiap.orbitagro.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
        @NotBlank @Size(max = 120) String logradouro,
        @NotBlank @Size(max = 80) String municipio,
        @NotBlank @Size(min = 2, max = 2) String uf,
        @Size(max = 10) String cep
) {
}
