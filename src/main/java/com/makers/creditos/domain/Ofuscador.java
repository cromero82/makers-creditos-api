package com.makers.creditos.domain;

public final class Ofuscador {

	private Ofuscador() {
	}

	public static String email(String email) {
		if (email == null || email.isBlank()) {
			return "***";
		}
		int arroba = email.indexOf('@');
		if (arroba <= 2) {
			return "***";
		}
		return email.substring(0, 2) + "***" + email.substring(arroba);
	}
}
