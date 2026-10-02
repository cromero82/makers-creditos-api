package com.makers.creditos.application;

import com.makers.creditos.application.port.PrestamoPort;
import com.makers.creditos.config.CacheConfig;
import com.makers.creditos.domain.excepcion.NegocioException;
import com.makers.creditos.domain.prestamo.EstadoPrestamo;
import com.makers.creditos.domain.prestamo.Prestamo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ResolverPrestamoUseCase {

	private static final Logger log = LoggerFactory.getLogger(ResolverPrestamoUseCase.class);

	private final PrestamoPort prestamos;

	public ResolverPrestamoUseCase(PrestamoPort prestamos) {
		this.prestamos = prestamos;
	}

	@Transactional
	@CacheEvict(cacheNames = CacheConfig.PRESTAMOS, allEntries = true)
	public Prestamo ejecutar(UUID id, EstadoPrestamo destino) {
		log.info("inicio resolver id={} destino={}", id, destino);
		try {
			Prestamo actual = prestamos.buscarPorId(id)
					.orElseThrow(() -> new NegocioException(NegocioException.Codigo.PRESTAMO_NO_ENCONTRADO));
			EstadoPrestamo anterior = actual.getEstado();
			Prestamo guardado = prestamos.guardar(actual.resolver(destino));
			log.info("resolver id={} de={} a={}", id, anterior, destino);
			return guardado;
		} catch (NegocioException ex) {
			log.warn("error de negocio codigo={} id={}", ex.getCodigo(), id);
			throw ex;
		} finally {
			log.info("fin resolver id={}", id);
		}
	}
}
