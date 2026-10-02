package com.makers.creditos.infrastructure.web.dto;

import com.makers.creditos.domain.usuario.Rol;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRespuesta {

	private final String token;
	private final String email;
	private final Rol rol;
}
