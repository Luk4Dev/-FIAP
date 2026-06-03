package br.com.fiap.orbitagro.dto.produtor;

import br.com.fiap.orbitagro.dto.common.EnderecoDTO;

public record ProdutorResponse(
        Long id,
        String nome,
        String documento,
        String email,
        String telefone,
        EnderecoDTO endereco,
        Long regiaoRuralId,
        String regiaoRuralNome,
        Long cooperativaId,
        String cooperativaNome
) {
}
