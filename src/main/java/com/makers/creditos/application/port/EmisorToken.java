package com.makers.creditos.application.port;

import com.makers.creditos.domain.usuario.Usuario;

public interface EmisorToken {

	String emitir(Usuario usuario);
}
