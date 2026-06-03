package br.com.fiap.orbitagro.dto.cooperativa;

import br.com.fiap.orbitagro.dto.common.EnderecoDTO;

public record CooperativaResponse(
        Long id,
        String nome,
        String cnpj,
        String email,
        String telefone,
        EnderecoDTO endereco
) {
}
