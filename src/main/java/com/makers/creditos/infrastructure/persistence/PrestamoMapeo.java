package com.makers.creditos.infrastructure.persistence;

import com.makers.creditos.domain.prestamo.Prestamo;

final class PrestamoMapeo {

	private PrestamoMapeo() {
	}

	static Prestamo aDominio(PrestamoEntity entity) {
		return Prestamo.builder()
				.id(entity.getId())
				.usuarioId(entity.getUsuarioId())
				.monto(entity.getMonto())
				.plazoMeses(entity.getPlazoMeses())
				.estado(entity.getEstado())
				.version(entity.getVersion())
				.creadoEn(entity.getCreadoEn())
				.build();
	}

	static void copiarNuevo(Prestamo prestamo, PrestamoEntity entity) {
		entity.setId(prestamo.getId());
		entity.setUsuarioId(prestamo.getUsuarioId());
		entity.setMonto(prestamo.getMonto());
		entity.setPlazoMeses(prestamo.getPlazoMeses());
		entity.setEstado(prestamo.getEstado());
		entity.setCreadoEn(prestamo.getCreadoEn());
	}
}
