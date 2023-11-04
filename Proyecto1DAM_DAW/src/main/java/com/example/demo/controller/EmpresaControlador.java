package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Empresa;
import com.example.demo.repository.EmpresaRepository;

@RestController
@RequestMapping("/empresas")
public class EmpresaControlador {
	@Autowired
	private final EmpresaRepository empresaRepo;
	
	public EmpresaControlador (EmpresaRepository empresaRepo) {
		this.empresaRepo = empresaRepo;
		
	}
	
	@GetMapping
	public ResponseEntity<List<Empresa>> getEmpresas() {
		List<Empresa> empresas = empresaRepo.findAll();
		return ResponseEntity.ok(empresas);
	}
	
}
