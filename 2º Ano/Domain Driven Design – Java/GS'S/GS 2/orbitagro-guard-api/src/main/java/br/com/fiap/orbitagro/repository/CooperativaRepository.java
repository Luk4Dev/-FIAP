package br.com.fiap.orbitagro.repository;

import br.com.fiap.orbitagro.domain.entity.Cooperativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CooperativaRepository extends JpaRepository<Cooperativa, Long> {
    Optional<Cooperativa> findByCnpj(String cnpj);
}
