package com.makers.creditos.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PrestamoJpaRepository extends JpaRepository<PrestamoEntity, UUID> {

	List<PrestamoEntity> findByUsuarioIdOrderByCreadoEnDesc(UUID usuarioId);

	List<PrestamoEntity> findAllByOrderByCreadoEnDesc();
}
