package com.makers.creditos.infrastructure.web.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlazoPermitidoValidator implements ConstraintValidator<PlazoPermitido, Integer> {

	@Override
	public boolean isValid(Integer plazo, ConstraintValidatorContext context) {
		if (plazo == null) {
			return true;
		}
		return plazo >= 1 && plazo <= 360;
	}
}
