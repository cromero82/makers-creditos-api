package com.makers.creditos.infrastructure.security;

import com.makers.creditos.application.port.ContrasenaPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ContrasenaPortAdaptador implements ContrasenaPort {

	private final PasswordEncoder passwordEncoder;

	public ContrasenaPortAdaptador(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public boolean coincide(String plano, String hash) {
		return passwordEncoder.matches(plano, hash);
	}
}
