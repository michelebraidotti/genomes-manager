package org.genomesmanager.domain.entities;

public class OutOfBoundsException extends Exception {
	private static final long serialVersionUID = 1L;

	public OutOfBoundsException(String message) {
		super(message);
	}

}
