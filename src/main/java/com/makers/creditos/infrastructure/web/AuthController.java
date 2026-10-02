package com.makers.creditos.infrastructure.web;

import com.makers.creditos.application.AutenticarUsuario;
import com.makers.creditos.application.LoginResultado;
import com.makers.creditos.infrastructure.web.dto.LoginRequest;
import com.makers.creditos.infrastructure.web.dto.LoginRespuesta;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AutenticarUsuario autenticarUsuario;

	public AuthController(AutenticarUsuario autenticarUsuario) {
		this.autenticarUsuario = autenticarUsuario;
	}

	@PostMapping("/login")
	public LoginRespuesta login(@Valid @RequestBody LoginRequest request) {
		LoginResultado resultado = autenticarUsuario.ejecutar(request.getEmail(), request.getPassword());
		return LoginRespuesta.builder()
				.token(resultado.getToken())
				.email(resultado.getEmail())
				.rol(resultado.getRol())
				.build();
	}
}
