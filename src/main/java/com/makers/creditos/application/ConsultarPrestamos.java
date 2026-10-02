package com.makers.creditos.application;

import com.makers.creditos.application.port.PrestamoRepositorio;
import com.makers.creditos.application.port.UsuarioRepositorio;
import com.makers.creditos.config.CacheConfig;
import com.makers.creditos.domain.Ofuscador;
import com.makers.creditos.domain.prestamo.Prestamo;
import com.makers.creditos.domain.usuario.Rol;
import com.makers.creditos.domain.usuario.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsultarPrestamos {

	private static final Logger log = LoggerFactory.getLogger(ConsultarPrestamos.class);

	private final PrestamoRepositorio prestamos;
	private final UsuarioRepositorio usuarios;

	public ConsultarPrestamos(PrestamoRepositorio prestamos, UsuarioRepositorio usuarios) {
		this.prestamos = prestamos;
		this.usuarios = usuarios;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheNames = CacheConfig.PRESTAMOS, key = "#actor.id")
	public List<PrestamoVisible> ejecutar(Usuario actor) {
		List<Prestamo> lista = actor.getRol() == Rol.ADMIN
				? prestamos.listar()
				: prestamos.buscarPorUsuario(actor.getId());
		log.info("consulta prestamos actor={} cantidad={}", Ofuscador.email(actor.getEmail()), lista.size());
		return lista.stream().map(this::visible).toList();
	}

	private PrestamoVisible visible(Prestamo prestamo) {
		String email = usuarios.buscarPorId(prestamo.getUsuarioId()).map(Usuario::getEmail).orElse("");
		return new PrestamoVisible(prestamo, email);
	}
}
