package com.makers.creditos.domain.excepcion;

import java.util.UUID;

public class PrestamoNoEncontradoException extends RuntimeException {

	public PrestamoNoEncontradoException(UUID id) {
		super("Préstamo no encontrado");
	}
}
