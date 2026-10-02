package com.makers.creditos.application;

import com.makers.creditos.domain.usuario.Rol;

public final class LoginResultado {

	private final String token;
	private final String email;
	private final Rol rol;

	public LoginResultado(String token, String email, Rol rol) {
		this.token = token;
		this.email = email;
		this.rol = rol;
	}

	public String getToken() {
		return token;
	}

	public String getEmail() {
		return email;
	}

	public Rol getRol() {
		return rol;
	}
}
