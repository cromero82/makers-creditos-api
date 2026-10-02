package com.makers.creditos.infrastructure.web.dto;

import com.makers.creditos.application.PrestamoVisible;
import com.makers.creditos.domain.prestamo.EstadoPrestamo;
import com.makers.creditos.domain.prestamo.Prestamo;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class PrestamoRespuesta {

	private final UUID id;
	private final String email;
	private final BigDecimal monto;
	private final int plazoMeses;
	private final EstadoPrestamo estado;
	private final OffsetDateTime creadoEn;

	public static PrestamoRespuesta de(PrestamoVisible visible) {
		Prestamo prestamo = visible.getPrestamo();
		return PrestamoRespuesta.builder()
				.id(prestamo.getId())
				.email(visible.getEmail())
				.monto(prestamo.getMonto())
				.plazoMeses(prestamo.getPlazoMeses())
				.estado(prestamo.getEstado())
				.creadoEn(prestamo.getCreadoEn())
				.build();
	}

	public static PrestamoRespuesta de(Prestamo prestamo, String email) {
		return PrestamoRespuesta.builder()
				.id(prestamo.getId())
				.email(email)
				.monto(prestamo.getMonto())
				.plazoMeses(prestamo.getPlazoMeses())
				.estado(prestamo.getEstado())
				.creadoEn(prestamo.getCreadoEn())
				.build();
	}
}
