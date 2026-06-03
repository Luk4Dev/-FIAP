package br.com.fiap.orbitagro.repository;

import br.com.fiap.orbitagro.domain.entity.EquipeResposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRespostaRepository extends JpaRepository<EquipeResposta, Long> {
    Page<EquipeResposta> findByDisponivelTrue(Pageable pageable);
}
