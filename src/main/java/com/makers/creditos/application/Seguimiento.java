package com.makers.creditos.application;

import com.makers.creditos.domain.Ofuscador;
import org.slf4j.MDC;

public final class Seguimiento {

	public static final String ACTOR = "actor";

	private Seguimiento() {
	}

	public static void actor(String email) {
		MDC.put(ACTOR, Ofuscador.email(email));
	}
}
