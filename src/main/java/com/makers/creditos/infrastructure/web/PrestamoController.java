package com.makers.creditos.infrastructure.web;

import com.makers.creditos.application.ConsultarPrestamos;
import com.makers.creditos.application.ResolverPrestamo;
import com.makers.creditos.application.SolicitarPrestamo;
import com.makers.creditos.domain.prestamo.Prestamo;
import com.makers.creditos.domain.usuario.Usuario;
import com.makers.creditos.infrastructure.security.UsuarioPrincipal;
import com.makers.creditos.infrastructure.web.dto.PrestamoRespuesta;
import com.makers.creditos.infrastructure.web.dto.ResolucionRequest;
import com.makers.creditos.infrastructure.web.dto.SolicitudPrestamoRequest;
import jakarta.validation.Valid;
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

	private final SolicitarPrestamo solicitarPrestamo;
	private final ConsultarPrestamos consultarPrestamos;
	private final ResolverPrestamo resolverPrestamo;

	public PrestamoController(SolicitarPrestamo solicitarPrestamo, ConsultarPrestamos consultarPrestamos,
			ResolverPrestamo resolverPrestamo) {
		this.solicitarPrestamo = solicitarPrestamo;
		this.consultarPrestamos = consultarPrestamos;
		this.resolverPrestamo = resolverPrestamo;
	}

	@PostMapping
	public PrestamoRespuesta solicitar(@Valid @RequestBody SolicitudPrestamoRequest request) {
		Usuario actor = actual();
		Prestamo prestamo = solicitarPrestamo.ejecutar(actor.getId(), request.getMonto(), request.getPlazoMeses());
		return PrestamoRespuesta.de(prestamo, actor.getEmail());
	}

	@GetMapping
	public List<PrestamoRespuesta> consultar() {
		return consultarPrestamos.ejecutar(actual()).stream().map(PrestamoRespuesta::de).toList();
	}

	@PatchMapping("/{id}/resolucion")
	@PreAuthorize("hasRole('ADMIN')")
	public PrestamoRespuesta resolver(@PathVariable UUID id, @Valid @RequestBody ResolucionRequest request) {
		Prestamo prestamo = resolverPrestamo.ejecutar(id, request.getEstado());
		return consultarPrestamos.ejecutar(actual()).stream()
				.map(PrestamoRespuesta::de)
				.filter(respuesta -> respuesta.getId().equals(prestamo.getId()))
				.findFirst()
				.orElseGet(() -> PrestamoRespuesta.de(prestamo, ""));
	}

	private static Usuario actual() {
		UsuarioPrincipal principal = (UsuarioPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal.getUsuario();
	}
}
