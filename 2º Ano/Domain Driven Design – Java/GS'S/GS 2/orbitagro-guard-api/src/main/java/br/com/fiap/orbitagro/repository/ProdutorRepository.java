package br.com.fiap.orbitagro.repository;

import br.com.fiap.orbitagro.domain.entity.Produtor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
    Page<Produtor> findByRegiaoRuralId(Long regiaoId, Pageable pageable);
}
