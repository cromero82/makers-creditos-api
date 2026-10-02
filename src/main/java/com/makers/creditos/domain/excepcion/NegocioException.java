package com.makers.creditos.domain.excepcion;

public class NegocioException extends RuntimeException {

	public enum Codigo {
		CREDENCIALES_INVALIDAS("Credenciales inválidas"),
		PRESTAMO_NO_ENCONTRADO("Préstamo no encontrado"),
		APROBACION_NO_PERMITIDA("El préstamo ya fue resuelto");

		private final String mensaje;

		Codigo(String mensaje) {
			this.mensaje = mensaje;
		}

		public String getMensaje() {
			return mensaje;
		}
	}

	private final Codigo codigo;

	public NegocioException(Codigo codigo) {
		super(codigo.getMensaje());
		this.codigo = codigo;
	}

	public Codigo getCodigo() {
		return codigo;
	}
}
