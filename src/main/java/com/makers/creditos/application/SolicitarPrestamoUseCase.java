package com.makers.creditos.application;

import com.makers.creditos.application.port.PrestamoPort;
import com.makers.creditos.config.CacheConfig;
import com.makers.creditos.domain.prestamo.EstadoPrestamo;
import com.makers.creditos.domain.prestamo.Prestamo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class SolicitarPrestamoUseCase {

	private static final Logger log = LoggerFactory.getLogger(SolicitarPrestamoUseCase.class);

	private final PrestamoPort prestamos;

	public SolicitarPrestamoUseCase(PrestamoPort prestamos) {
		this.prestamos = prestamos;
	}

	@Transactional
	@CacheEvict(cacheNames = CacheConfig.PRESTAMOS, allEntries = true)
	public Prestamo ejecutar(UUID usuarioId, BigDecimal monto, int plazoMeses) {
		log.info("inicio solicitar usuarioId={}", usuarioId);
		Prestamo prestamo = Prestamo.builder()
				.id(UUID.randomUUID())
				.usuarioId(usuarioId)
				.monto(monto)
				.plazoMeses(plazoMeses)
				.estado(EstadoPrestamo.PENDIENTE)
				.creadoEn(OffsetDateTime.now())
				.build();
		Prestamo guardado = prestamos.guardar(prestamo);
		log.info("fin solicitar id={} estado={}", guardado.getId(), guardado.getEstado());
		return guardado;
	}
}
