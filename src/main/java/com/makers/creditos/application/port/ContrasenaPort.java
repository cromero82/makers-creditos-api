package com.makers.creditos.application.port;

public interface ContrasenaPort {

	boolean coincide(String plano, String hash);
}
