package com.example.demo.exception;

public class OfertaNotFoundException extends RuntimeException{
	public OfertaNotFoundException(int id) {
		super("Oferta no encotrada con id " + id);
	}
}