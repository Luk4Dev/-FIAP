package br.com.fiap.orbitagro.repository;

import br.com.fiap.orbitagro.domain.entity.RegiaoRural;
import br.com.fiap.orbitagro.domain.enums.NivelConectividade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegiaoRuralRepository extends JpaRepository<RegiaoRural, Long> {
    Page<RegiaoRural> findByUfIgnoreCase(String uf, Pageable pageable);
    Page<RegiaoRural> findByNivelConectividade(NivelConectividade nivelConectividade, Pageable pageable);
}
