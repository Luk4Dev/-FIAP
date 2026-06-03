package br.com.fiap.orbitagro.repository;

import br.com.fiap.orbitagro.domain.entity.DadoOrbital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DadoOrbitalRepository extends JpaRepository<DadoOrbital, Long> {
    Page<DadoOrbital> findByRegiaoRuralId(Long regiaoId, Pageable pageable);
    Optional<DadoOrbital> findTopByRegiaoRuralIdOrderByDataColetaDesc(Long regiaoId);
}
