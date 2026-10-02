package com.makers.creditos.application;

import com.makers.creditos.application.port.PrestamoRepositorio;
import com.makers.creditos.config.CacheConfig;
import com.makers.creditos.domain.excepcion.PrestamoNoEncontradoException;
import com.makers.creditos.domain.prestamo.EstadoPrestamo;
import com.makers.creditos.domain.prestamo.Prestamo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ResolverPrestamo {

	private static final Logger log = LoggerFactory.getLogger(ResolverPrestamo.class);

	private final PrestamoRepositorio prestamos;

	public ResolverPrestamo(PrestamoRepositorio prestamos) {
		this.prestamos = prestamos;
	}

	@Transactional
	@CacheEvict(cacheNames = CacheConfig.PRESTAMOS, allEntries = true)
	public Prestamo ejecutar(UUID id, EstadoPrestamo destino) {
		Prestamo actual = prestamos.buscarPorId(id).orElseThrow(() -> new PrestamoNoEncontradoException(id));
		EstadoPrestamo anterior = actual.getEstado();
		Prestamo guardado = prestamos.guardar(actual.resolver(destino));
		log.info("prestamo resuelto id={} de={} a={}", id, anterior, destino);
		return guardado;
	}
}
