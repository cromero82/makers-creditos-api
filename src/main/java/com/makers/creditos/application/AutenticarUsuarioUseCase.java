package com.makers.creditos.application;

import com.makers.creditos.application.port.ContrasenaPort;
import com.makers.creditos.application.port.TokenPort;
import com.makers.creditos.application.port.UsuarioPort;
import com.makers.creditos.domain.excepcion.NegocioException;
import com.makers.creditos.domain.usuario.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioUseCase {

	private static final Logger log = LoggerFactory.getLogger(AutenticarUsuarioUseCase.class);

	private final UsuarioPort usuarios;
	private final ContrasenaPort contrasena;
	private final TokenPort token;

	public AutenticarUsuarioUseCase(UsuarioPort usuarios, ContrasenaPort contrasena, TokenPort token) {
		this.usuarios = usuarios;
		this.contrasena = contrasena;
		this.token = token;
	}

	public LoginResultado ejecutar(String email, String password) {
		Seguimiento.actor(email);
		log.info("inicio autenticar");
		try {
			Usuario usuario = usuarios.buscarPorEmail(email)
					.orElseThrow(() -> new NegocioException(NegocioException.Codigo.CREDENCIALES_INVALIDAS));
			if (!contrasena.coincide(password, usuario.getPasswordHash())) {
				throw new NegocioException(NegocioException.Codigo.CREDENCIALES_INVALIDAS);
			}
			log.info("autenticar resultado=ok");
			return new LoginResultado(token.emitir(usuario), usuario.getEmail(), usuario.getRol());
		} catch (NegocioException ex) {
			log.warn("error de negocio codigo={}", ex.getCodigo());
			throw ex;
		} finally {
			log.info("fin autenticar");
		}
	}
}
