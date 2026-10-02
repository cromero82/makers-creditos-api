package com.makers.creditos.application.port;

public interface VerificadorContrasena {

	boolean coincide(String plano, String hash);
}
