package com.makers.creditos.domain.usuario;

import com.makers.creditos.domain.Ofuscador;

import java.util.UUID;

public final class Usuario {

	private final UUID id;
	private final String email;
	private final String passwordHash;
	private final Rol rol;

	public Usuario(UUID id, String email, String passwordHash, Rol rol) {
		this.id = id;
		this.email = email;
		this.passwordHash = passwordHash;
		this.rol = rol;
	}

	public UUID getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public Rol getRol() {
		return rol;
	}

	@Override
	public String toString() {
		return "Usuario{email=" + Ofuscador.email(email) + ", rol=" + rol + "}";
	}
}
