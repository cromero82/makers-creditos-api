package com.makers.creditos.application.port;

import com.makers.creditos.domain.usuario.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioPort {

	Optional<Usuario> buscarPorEmail(String email);

	Optional<Usuario> buscarPorId(UUID id);
}
