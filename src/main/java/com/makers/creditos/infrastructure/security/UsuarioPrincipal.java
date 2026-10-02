package com.makers.creditos.infrastructure.security;

import com.makers.creditos.domain.usuario.Rol;
import com.makers.creditos.domain.usuario.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public final class UsuarioPrincipal implements UserDetails {

	private final Usuario usuario;

	public UsuarioPrincipal(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	public boolean es(Rol rol) {
		return usuario.getRol() == rol;
	}
}
