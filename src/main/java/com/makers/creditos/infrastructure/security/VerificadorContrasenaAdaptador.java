package com.makers.creditos.infrastructure.security;

import com.makers.creditos.application.port.VerificadorContrasena;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class VerificadorContrasenaAdaptador implements VerificadorContrasena {

	private final PasswordEncoder passwordEncoder;

	public VerificadorContrasenaAdaptador(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public boolean coincide(String plano, String hash) {
		return passwordEncoder.matches(plano, hash);
	}
}
