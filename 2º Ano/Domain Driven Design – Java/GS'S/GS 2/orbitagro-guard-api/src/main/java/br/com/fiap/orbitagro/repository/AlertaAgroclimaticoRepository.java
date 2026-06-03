package br.com.fiap.orbitagro.repository;

import br.com.fiap.orbitagro.domain.entity.AlertaAgroclimatico;
import br.com.fiap.orbitagro.domain.enums.NivelRisco;
import br.com.fiap.orbitagro.domain.enums.StatusAlerta;
import br.com.fiap.orbitagro.domain.enums.TipoRisco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AlertaAgroclimaticoRepository extends JpaRepository<AlertaAgroclimatico, Long> {
    Page<AlertaAgroclimatico> findByStatus(StatusAlerta status, Pageable pageable);
    Page<AlertaAgroclimatico> findByNivelRisco(NivelRisco nivelRisco, Pageable pageable);
    Optional<AlertaAgroclimatico> findFirstByRegiaoRuralIdAndTipoRiscoAndStatusAndDataGeracaoAfter(
            Long regiaoId,
            TipoRisco tipoRisco,
            StatusAlerta status,
            LocalDateTime dataLimite
    );
}
