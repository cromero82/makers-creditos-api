package com.makers.creditos.domain.prestamo;

import com.makers.creditos.domain.excepcion.NegocioException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public final class Prestamo {

	private final UUID id;
	private final UUID usuarioId;
	private final BigDecimal monto;
	private final int plazoMeses;
	private final EstadoPrestamo estado;
	private final Long version;
	private final OffsetDateTime creadoEn;

	private Prestamo(UUID id, UUID usuarioId, BigDecimal monto, int plazoMeses, EstadoPrestamo estado,
			Long version, OffsetDateTime creadoEn) {
		this.id = id;
		this.usuarioId = usuarioId;
		this.monto = monto;
		this.plazoMeses = plazoMeses;
		this.estado = estado;
		this.version = version;
		this.creadoEn = creadoEn;
	}

	public static Builder builder() {
		return new Builder();
	}

	public Prestamo resolver(EstadoPrestamo destino) {
		if (estado != EstadoPrestamo.PENDIENTE || destino == EstadoPrestamo.PENDIENTE) {
			throw new NegocioException(NegocioException.Codigo.APROBACION_NO_PERMITIDA);
		}
		return builder()
				.id(id)
				.usuarioId(usuarioId)
				.monto(monto)
				.plazoMeses(plazoMeses)
				.estado(destino)
				.version(version)
				.creadoEn(creadoEn)
				.build();
	}

	public UUID getId() {
		return id;
	}

	public UUID getUsuarioId() {
		return usuarioId;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public int getPlazoMeses() {
		return plazoMeses;
	}

	public EstadoPrestamo getEstado() {
		return estado;
	}

	public Long getVersion() {
		return version;
	}

	public OffsetDateTime getCreadoEn() {
		return creadoEn;
	}

	@Override
	public String toString() {
		return "Prestamo{id=" + id + ", estado=" + estado + "}";
	}

	public static final class Builder {

		private UUID id;
		private UUID usuarioId;
		private BigDecimal monto;
		private int plazoMeses;
		private EstadoPrestamo estado;
		private Long version;
		private OffsetDateTime creadoEn;

		public Builder id(UUID id) {
			this.id = id;
			return this;
		}

		public Builder usuarioId(UUID usuarioId) {
			this.usuarioId = usuarioId;
			return this;
		}

		public Builder monto(BigDecimal monto) {
			this.monto = monto;
			return this;
		}

		public Builder plazoMeses(int plazoMeses) {
			this.plazoMeses = plazoMeses;
			return this;
		}

		public Builder estado(EstadoPrestamo estado) {
			this.estado = estado;
			return this;
		}

		public Builder version(Long version) {
			this.version = version;
			return this;
		}

		public Builder creadoEn(OffsetDateTime creadoEn) {
			this.creadoEn = creadoEn;
			return this;
		}

		public Prestamo build() {
			if (id == null || usuarioId == null || monto == null || estado == null || creadoEn == null) {
				throw new IllegalStateException("Prestamo incompleto");
			}
			if (monto.signum() <= 0 || plazoMeses < 1) {
				throw new IllegalStateException("Prestamo incompleto");
			}
			return new Prestamo(id, usuarioId, monto, plazoMeses, estado, version, creadoEn);
		}
	}
}
