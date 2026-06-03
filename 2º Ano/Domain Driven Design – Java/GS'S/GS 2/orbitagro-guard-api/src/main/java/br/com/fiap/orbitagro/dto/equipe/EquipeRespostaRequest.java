package br.com.fiap.orbitagro.dto.equipe;

import br.com.fiap.orbitagro.domain.enums.TipoEquipe;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EquipeRespostaRequest(
        @NotBlank @Size(max = 120) String nome,
        @NotNull TipoEquipe tipoEquipe,
        @NotNull @Min(1) Integer capacidadeAtendimentoDiaria,
        @Size(max = 20) String telefoneContato
) {
}
