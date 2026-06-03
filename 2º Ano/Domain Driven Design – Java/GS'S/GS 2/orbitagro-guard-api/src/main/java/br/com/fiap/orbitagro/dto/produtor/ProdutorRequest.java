package br.com.fiap.orbitagro.dto.produtor;

import br.com.fiap.orbitagro.dto.common.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutorRequest(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Size(max = 20) String documento,
        @Email @Size(max = 120) String email,
        @Size(max = 20) String telefone,
        @Valid EnderecoDTO endereco,
        @NotNull Long regiaoRuralId,
        Long cooperativaId
) {
}
