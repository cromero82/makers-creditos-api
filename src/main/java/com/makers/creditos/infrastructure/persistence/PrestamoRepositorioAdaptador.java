package com.makers.creditos.infrastructure.persistence;

import com.makers.creditos.application.port.PrestamoRepositorio;
import com.makers.creditos.domain.prestamo.Prestamo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PrestamoRepositorioAdaptador implements PrestamoRepositorio {

	private final PrestamoJpaRepository jpa;

	public PrestamoRepositorioAdaptador(PrestamoJpaRepository jpa) {
		this.jpa = jpa;
	}

	@Override
	public Prestamo guardar(Prestamo prestamo) {
		Optional<PrestamoEntity> existente = jpa.findById(prestamo.getId());
		if (existente.isPresent()) {
			PrestamoEntity entity = existente.get();
			entity.setEstado(prestamo.getEstado());
			return PrestamoMapeo.aDominio(entity);
		}
		PrestamoEntity entity = new PrestamoEntity();
		PrestamoMapeo.copiarNuevo(prestamo, entity);
		return PrestamoMapeo.aDominio(jpa.save(entity));
	}

	@Override
	public Optional<Prestamo> buscarPorId(UUID id) {
		return jpa.findById(id).map(PrestamoMapeo::aDominio);
	}

	@Override
	public List<Prestamo> buscarPorUsuario(UUID usuarioId) {
		return jpa.findByUsuarioIdOrderByCreadoEnDesc(usuarioId).stream().map(PrestamoMapeo::aDominio).toList();
	}

	@Override
	public List<Prestamo> listar() {
		return jpa.findAllByOrderByCreadoEnDesc().stream().map(PrestamoMapeo::aDominio).toList();
	}
}
