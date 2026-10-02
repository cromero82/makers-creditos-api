package com.makers.creditos.infrastructure.web;

import com.makers.creditos.application.AutenticarUsuarioUseCase;
import com.makers.creditos.application.LoginResultado;
import com.makers.creditos.domain.excepcion.NegocioException;
import com.makers.creditos.infrastructure.web.dto.LoginRequest;
import com.makers.creditos.infrastructure.web.dto.LoginRespuesta;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AutenticarUsuarioUseCase autenticarUsuario;

	public AuthController(AutenticarUsuarioUseCase autenticarUsuario) {
		this.autenticarUsuario = autenticarUsuario;
	}

	@PostMapping("/login")
	public LoginRespuesta login(@Valid @RequestBody LoginRequest request) {
		log.info("inicio login");
		try {
			LoginResultado resultado = autenticarUsuario.ejecutar(request.getEmail(), request.getPassword());
			return LoginRespuesta.builder()
					.token(resultado.getToken())
					.email(resultado.getEmail())
					.rol(resultado.getRol())
					.build();
		} catch (NegocioException ex) {
			log.warn("error de negocio codigo={}", ex.getCodigo());
			throw ex;
		} finally {
			log.info("fin login");
		}
	}
}
