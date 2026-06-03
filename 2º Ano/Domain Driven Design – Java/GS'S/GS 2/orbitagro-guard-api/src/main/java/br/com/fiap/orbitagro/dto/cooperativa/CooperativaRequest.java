package br.com.fiap.orbitagro.dto.cooperativa;

import br.com.fiap.orbitagro.dto.common.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CooperativaRequest(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Size(max = 20) String cnpj,
        @Email @Size(max = 120) String email,
        @Size(max = 20) String telefone,
        @Valid EnderecoDTO endereco
) {
}
