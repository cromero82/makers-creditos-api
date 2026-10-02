package com.makers.creditos.infrastructure.persistence;

import com.makers.creditos.domain.usuario.Usuario;

final class UsuarioMapeo {

	private UsuarioMapeo() {
	}

	static Usuario aDominio(UsuarioEntity entity) {
		return new Usuario(entity.getId(), entity.getEmail(), entity.getPasswordHash(), entity.getRol());
	}
}
