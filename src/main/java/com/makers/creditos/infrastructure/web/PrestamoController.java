package com.makers.creditos.infrastructure.web;

import com.makers.creditos.application.ConsultarPrestamosUseCase;
import com.makers.creditos.application.ResolverPrestamoUseCase;
import com.makers.creditos.application.SolicitarPrestamoUseCase;
import com.makers.creditos.domain.Ofuscador;
import com.makers.creditos.domain.excepcion.NegocioException;
import com.makers.creditos.domain.prestamo.Prestamo;
import com.makers.creditos.domain.usuario.Usuario;
import com.makers.creditos.infrastructure.security.UsuarioPrincipal;
import com.makers.creditos.infrastructure.web.dto.PrestamoRespuesta;
import com.makers.creditos.infrastructure.web.dto.ResolucionRequest;
import com.makers.creditos.infrastructure.web.dto.SolicitudPrestamoRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

	private static final Logger log = LoggerFactory.getLogger(PrestamoController.class);

	private final SolicitarPrestamoUseCase solicitarPrestamo;
	private final ConsultarPrestamosUseCase consultarPrestamos;
	private final ResolverPrestamoUseCase resolverPrestamo;

	public PrestamoController(SolicitarPrestamoUseCase solicitarPrestamo, ConsultarPrestamosUseCase consultarPrestamos,
			ResolverPrestamoUseCase resolverPrestamo) {
		this.solicitarPrestamo = solicitarPrestamo;
		this.consultarPrestamos = consultarPrestamos;
		this.resolverPrestamo = resolverPrestamo;
	}

	@PostMapping
	public PrestamoRespuesta solicitar(@Valid @RequestBody SolicitudPrestamoRequest request) {
		Usuario actor = actual();
		String quien = Ofuscador.email(actor.getEmail());
		log.info("inicio solicitar actor={}", quien);
		try {
			Prestamo prestamo = solicitarPrestamo.ejecutar(actor.getId(), request.getMonto(), request.getPlazoMeses());
			log.info("solicitar id={}", prestamo.getId());
			return PrestamoRespuesta.de(prestamo, actor.getEmail());
		} catch (NegocioException ex) {
			log.warn("error de negocio codigo={} actor={}", ex.getCodigo(), quien);
			throw ex;
		} finally {
			log.info("fin solicitar actor={}", quien);
		}
	}

	@GetMapping
	public List<PrestamoRespuesta> consultar() {
		Usuario actor = actual();
		String quien = Ofuscador.email(actor.getEmail());
		log.info("inicio consultar actor={}", quien);
		try {
			List<PrestamoRespuesta> respuesta = consultarPrestamos.ejecutar(actor).stream()
					.map(PrestamoRespuesta::de)
					.toList();
			log.info("consultar cantidad={}", respuesta.size());
			return respuesta;
		} catch (NegocioException ex) {
			log.warn("error de negocio codigo={} actor={}", ex.getCodigo(), quien);
			throw ex;
		} finally {
			log.info("fin consultar actor={}", quien);
		}
	}

	@PatchMapping("/{id}/resolucion")
	@PreAuthorize("hasRole('ADMIN')")
	public PrestamoRespuesta resolver(@PathVariable UUID id, @Valid @RequestBody ResolucionRequest request) {
		Usuario actor = actual();
		String quien = Ofuscador.email(actor.getEmail());
		log.info("inicio resolver actor={} id={}", quien, id);
		try {
			Prestamo prestamo = resolverPrestamo.ejecutar(id, request.getEstado());
			log.info("resolver id={} estado={}", prestamo.getId(), prestamo.getEstado());
			return consultarPrestamos.ejecutar(actor).stream()
					.map(PrestamoRespuesta::de)
					.filter(respuesta -> respuesta.getId().equals(prestamo.getId()))
					.findFirst()
					.orElseGet(() -> PrestamoRespuesta.de(prestamo, ""));
		} catch (NegocioException ex) {
			log.warn("error de negocio codigo={} actor={} id={}", ex.getCodigo(), quien, id);
			throw ex;
		} finally {
			log.info("fin resolver actor={} id={}", quien, id);
		}
	}

	private static Usuario actual() {
		UsuarioPrincipal principal = (UsuarioPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return principal.getUsuario();
	}
}
