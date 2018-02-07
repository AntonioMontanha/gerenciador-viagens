package com.montanha.gerenciador.services.exceptions;

public class ViagemServiceException extends RuntimeException {

	private static final long serialVersionUID = -1402677565107062800L;

	public ViagemServiceException(String mensagem) {
		super(mensagem);
	}

	public ViagemServiceException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
