package com.makers.creditos.application;

import com.makers.creditos.application.port.EmisorToken;
import com.makers.creditos.application.port.UsuarioRepositorio;
import com.makers.creditos.application.port.VerificadorContrasena;
import com.makers.creditos.domain.Ofuscador;
import com.makers.creditos.domain.excepcion.CredencialesInvalidasException;
import com.makers.creditos.domain.usuario.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuario {

	private static final Logger log = LoggerFactory.getLogger(AutenticarUsuario.class);

	private final UsuarioRepositorio usuarios;
	private final VerificadorContrasena verificador;
	private final EmisorToken emisorToken;

	public AutenticarUsuario(UsuarioRepositorio usuarios, VerificadorContrasena verificador, EmisorToken emisorToken) {
		this.usuarios = usuarios;
		this.verificador = verificador;
		this.emisorToken = emisorToken;
	}

	public LoginResultado ejecutar(String email, String password) {
		Usuario usuario = usuarios.buscarPorEmail(email).orElseThrow(CredencialesInvalidasException::new);
		if (!verificador.coincide(password, usuario.getPasswordHash())) {
			throw new CredencialesInvalidasException();
		}
		log.info("sesion iniciada actor={}", Ofuscador.email(usuario.getEmail()));
		return new LoginResultado(emisorToken.emitir(usuario), usuario.getEmail(), usuario.getRol());
	}
}
