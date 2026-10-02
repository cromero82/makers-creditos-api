package com.makers.creditos.application.port;

import com.makers.creditos.domain.prestamo.Prestamo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrestamoPort {

	Prestamo guardar(Prestamo prestamo);

	Optional<Prestamo> buscarPorId(UUID id);

	List<Prestamo> buscarPorUsuario(UUID usuarioId);

	List<Prestamo> listar();
}
