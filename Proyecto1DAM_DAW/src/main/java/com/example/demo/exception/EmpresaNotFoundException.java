package com.example.demo.exception;

public class EmpresaNotFoundException extends RuntimeException{
	public EmpresaNotFoundException(int id) {
		super("Empresa no encotrada con id " + id);
	}
}