package com.makers.creditos.application.port;

import com.makers.creditos.domain.usuario.Usuario;

public interface TokenPort {

	String emitir(Usuario usuario);
}
