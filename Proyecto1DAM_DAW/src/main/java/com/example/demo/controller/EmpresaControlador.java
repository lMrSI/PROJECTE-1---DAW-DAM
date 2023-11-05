package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("")
public class EmpresaControlador {
	@Autowired
	private final EmpresaRepository repository;
	
	public EmpresaControlador (EmpresaRepository repository) {
		this.repository = repository;
		
	}
	
	@GetMapping("/api/empresas")
	public ResponseEntity<List<Empresa>> readEmpresas() {
		List<Empresa> empresas = repository.findAll();
		return ResponseEntity.ok(empresas);
	}
	
	
	
	
	@GetMapping("/api/empresas/{id}")
	public ResponseEntity<Empresa> readEmpresa(@PathVariable int id) {
		Optional<Empresa> empresaBuscada = repository.findById(id);
		
		if (empresaBuscada.isPresent())
			return ResponseEntity.ok(empresaBuscada.get());
		else
			return ResponseEntity.noContent().build();
	}
	
	
	
	
	@PostMapping("/api/empresas")
	public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa) {
		Empresa empresaNueva = repository.save(empresa);
		return ResponseEntity.ok(empresaNueva);
	}
	
	
	
	
	@PutMapping("/api/empresas/{id}")
	public ResponseEntity<Empresa> updateEmpresa(@PathVariable int id, @RequestBody Empresa empresa) {
		Optional<Empresa> empresaBuscada = repository.findById(id);
		if (empresaBuscada.isPresent()) {
			Empresa empresaCambiada = empresaBuscada.get();
			empresaCambiada.setNombre(empresa.getNombre());
			empresaCambiada.setSector(empresa.getSector());
			empresaCambiada.setTamaño(empresa.getTamaño());
			empresaCambiada.setTipo(empresa.getTipo());
			empresaCambiada.setUbicacion(empresa.getUbicacion());
			repository.save(empresaCambiada);
			return ResponseEntity.ok(empresaCambiada);
		}
		else {
			return ResponseEntity.noContent().build();
		}
	}
	
	
	
	
	@DeleteMapping("/api/empresas/{id}")
	public ResponseEntity<Void> deleteEmpresa(@PathVariable int id) {
		repository.deleteById(id);
		return ResponseEntity.ok(null);
	}
	
}
