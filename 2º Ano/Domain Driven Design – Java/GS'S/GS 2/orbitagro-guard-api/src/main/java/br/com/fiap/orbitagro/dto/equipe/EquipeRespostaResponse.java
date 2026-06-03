package br.com.fiap.orbitagro.dto.equipe;

import br.com.fiap.orbitagro.domain.enums.TipoEquipe;

public record EquipeRespostaResponse(
        Long id,
        String nome,
        TipoEquipe tipoEquipe,
        boolean disponivel,
        Integer capacidadeAtendimentoDiaria,
        String telefoneContato
) {
}
