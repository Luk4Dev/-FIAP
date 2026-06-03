package br.com.fiap.orbitagro.repository;

import br.com.fiap.orbitagro.domain.entity.RotaAtendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RotaAtendimentoRepository extends JpaRepository<RotaAtendimento, Long> {
    Page<RotaAtendimento> findByAlertaId(Long alertaId, Pageable pageable);
}
