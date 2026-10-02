package com.makers.creditos.infrastructure.web;

import com.makers.creditos.domain.excepcion.NegocioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(NegocioException.class)
	ProblemDetail negocio(NegocioException ex) {
		log.warn("respuesta de negocio codigo={}", ex.getCodigo());
		return ProblemDetail.forStatusAndDetail(estado(ex.getCodigo()), ex.getMessage());
	}

	private static HttpStatus estado(NegocioException.Codigo codigo) {
		return switch (codigo) {
			case CREDENCIALES_INVALIDAS -> HttpStatus.UNAUTHORIZED;
			case PRESTAMO_NO_ENCONTRADO -> HttpStatus.NOT_FOUND;
			case APROBACION_NO_PERMITIDA -> HttpStatus.CONFLICT;
		};
	}

	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	ProblemDetail version(ObjectOptimisticLockingFailureException ex) {
		log.warn("conflicto de version al resolver prestamo");
		return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
				"El préstamo fue modificado. Consulta de nuevo.");
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	ProblemDetail denegado(AuthorizationDeniedException ex) {
		log.warn("acceso denegado");
		return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "No autorizado");
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	ProblemDetail cuerpo(HttpMessageNotReadableException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Datos inválidos");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ProblemDetail validacion(MethodArgumentNotValidException ex) {
		ProblemDetail detalle = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Datos inválidos");
		List<String> errores = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.toList();
		detalle.setProperty("errores", errores);
		return detalle;
	}

	@ExceptionHandler(Exception.class)
	ProblemDetail noPrevisto(Exception ex) {
		log.error("error no previsto", ex);
		return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno");
	}
}
