package com.makers.creditos.infrastructure.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SolicitudLogFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(SolicitudLogFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		log.info("inicio {} {}", request.getMethod(), request.getRequestURI());
		try {
			chain.doFilter(request, response);
		} finally {
			log.info("fin {} {} status={}", request.getMethod(), request.getRequestURI(), response.getStatus());
			MDC.clear();
		}
	}
}
