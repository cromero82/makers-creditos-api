package com.makers.creditos.domain.excepcion;

public class EstadoNoPermiteResolucionException extends RuntimeException {

	public EstadoNoPermiteResolucionException() {
		super("El préstamo ya fue resuelto");
	}
}
