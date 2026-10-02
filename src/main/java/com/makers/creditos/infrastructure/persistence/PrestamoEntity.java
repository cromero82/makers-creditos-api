package com.makers.creditos.infrastructure.persistence;

import com.makers.creditos.domain.prestamo.EstadoPrestamo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "prestamo")
@Getter
@Setter
@NoArgsConstructor
public class PrestamoEntity {

	@Id
	private UUID id;

	@Column(name = "usuario_id", nullable = false)
	private UUID usuarioId;

	@Column(nullable = false, precision = 14, scale = 2)
	private BigDecimal monto;

	@Column(name = "plazo_meses", nullable = false)
	private int plazoMeses;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private EstadoPrestamo estado;

	@Version
	@Column(nullable = false)
	private Long version;

	@Column(name = "creado_en", nullable = false)
	private OffsetDateTime creadoEn;

	@Override
	public String toString() {
		return "PrestamoEntity{id=" + id + ", estado=" + estado + "}";
	}
}
