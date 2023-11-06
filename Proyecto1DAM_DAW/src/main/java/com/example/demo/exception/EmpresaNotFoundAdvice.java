package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmpresaNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(EmpresaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String empresaNotFoundHandler(EmpresaNotFoundException ex) {
		return ex.getMessage();
	}
}