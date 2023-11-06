package com.example.demo.exception;

public class CurriculumNotFoundException extends RuntimeException{
	public CurriculumNotFoundException(int id) {
		super("Curriculum no encotrado con id " + id);
	}
}