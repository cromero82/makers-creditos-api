package com.makers.creditos.application;

import com.makers.creditos.domain.Ofuscador;
import com.makers.creditos.domain.prestamo.Prestamo;

public final class PrestamoVisible {

	private final Prestamo prestamo;
	private final String email;

	public PrestamoVisible(Prestamo prestamo, String email) {
		this.prestamo = prestamo;
		this.email = email;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "PrestamoVisible{prestamo=" + prestamo + ", email=" + Ofuscador.email(email) + "}";
	}
}
