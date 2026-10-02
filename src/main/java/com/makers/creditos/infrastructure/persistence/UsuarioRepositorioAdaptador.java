package com.makers.creditos.infrastructure.persistence;

import com.makers.creditos.application.port.UsuarioRepositorio;
import com.makers.creditos.domain.usuario.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioRepositorioAdaptador implements UsuarioRepositorio {

	private final UsuarioJpaRepository jpa;

	public UsuarioRepositorioAdaptador(UsuarioJpaRepository jpa) {
		this.jpa = jpa;
	}

	@Override
	public Optional<Usuario> buscarPorEmail(String email) {
		return jpa.findByEmail(email).map(UsuarioMapeo::aDominio);
	}

	@Override
	public Optional<Usuario> buscarPorId(UUID id) {
		return jpa.findById(id).map(UsuarioMapeo::aDominio);
	}
}
