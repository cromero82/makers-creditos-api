package com.makers.creditos.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SolicitudPrestamoRequest {

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal monto;

	@NotNull
	@PlazoPermitido
	private Integer plazoMeses;
}
