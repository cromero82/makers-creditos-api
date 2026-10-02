package com.makers.creditos.infrastructure.web.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlazoPermitidoValidator.class)
public @interface PlazoPermitido {

	String message() default "El plazo debe estar entre 1 y 360 meses";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
