package com.makers.creditos.infrastructure.security;

import com.makers.creditos.application.port.TokenPort;
import com.makers.creditos.application.port.UsuarioPort;
import com.makers.creditos.config.JwtProperties;
import com.makers.creditos.domain.usuario.Rol;
import com.makers.creditos.domain.usuario.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService implements TokenPort {

	private final JwtProperties properties;
	private final UsuarioPort usuarios;

	public JwtService(JwtProperties properties, UsuarioPort usuarios) {
		this.properties = properties;
		this.usuarios = usuarios;
	}

	@Override
	public String emitir(Usuario usuario) {
		Date ahora = new Date();
		Date expira = new Date(ahora.getTime() + properties.getExpirationMinutes() * 60_000);
		return Jwts.builder()
				.subject(usuario.getId().toString())
				.claim("rol", usuario.getRol().name())
				.issuedAt(ahora)
				.expiration(expira)
				.signWith(llave())
				.compact();
	}

	public Usuario leer(String token) {
		Claims claims = Jwts.parser().verifyWith(llave()).build().parseSignedClaims(token).getPayload();
		UUID id = UUID.fromString(claims.getSubject());
		Rol rol = Rol.valueOf(claims.get("rol", String.class));
		return usuarios.buscarPorId(id)
				.filter(usuario -> usuario.getRol() == rol)
				.orElseThrow(() -> new IllegalArgumentException("Token sin usuario"));
	}

	private SecretKey llave() {
		String secret = properties.getSecret();
		if (secret == null || secret.length() < 32) {
			throw new IllegalStateException("JWT_SECRET debe tener al menos 32 caracteres");
		}
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
}
