package com.makers.creditos.infrastructure.web.dto;

import com.makers.creditos.domain.prestamo.EstadoPrestamo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResolucionRequest {

	@NotNull
	private EstadoPrestamo estado;
}
