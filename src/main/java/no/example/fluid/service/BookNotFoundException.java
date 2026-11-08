package no.example.fluid.service;

public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException(String message) {
		super(message);
	}
}
