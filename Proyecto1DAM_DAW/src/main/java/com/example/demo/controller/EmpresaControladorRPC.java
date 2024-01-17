package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

import jakarta.persistence.EntityNotFoundException;

//@RestController
public class EmpresaControladorRPC {
	
	
	//@Autowired
	private final EmpresaRepository repository;
	
	
	public EmpresaControladorRPC (EmpresaRepository repository) {
		this.repository = repository;	
	}
	
	
	@GetMapping("/rpc/empresas")
	public ResponseEntity<List<Empresa>> readEmpresas() {
		//return repository.findAll()
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok(repository.findAll());
		
	}
	
	
	
	
	@GetMapping("/rpc/empresas/{id}")
	public ResponseEntity<Empresa> readEmpresa(@PathVariable int id) {
		//return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con ID: " + id));
		SecurityContextHolder.getContext().getAuthentication();
		return repository.findById(id).map( empresa -> { return ResponseEntity.ok(repository.save(empresa)); } )
									  .orElse(ResponseEntity.noContent().build());
	}
	
	
	
	
	@PostMapping("/rpc/empresas")
	public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa) {
		//return repository.save(empresa);
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok(repository.save(empresa));
	}
	
	
	
	
	@PutMapping("/rpc/empresas/{id}")
	public ResponseEntity<Empresa> updateEmpresa(@PathVariable int id, @RequestBody Empresa empresa) {
		return repository.findById(id)
				.map(empresaCambiada -> {
			    	empresaCambiada.setNombre(empresa.getNombre());
					empresaCambiada.setSector(empresa.getSector());
					empresaCambiada.setTamaño(empresa.getTamaño());
					empresaCambiada.setTipo(empresa.getTipo());
					empresaCambiada.setUbicacion(empresa.getUbicacion());
					//return repository.save(empresaCambiada);
					SecurityContextHolder.getContext().getAuthentication();
					return ResponseEntity.ok(repository.save(empresaCambiada));
	    		})
			    .orElseGet( () -> {
			        empresa.setIdEmpresa(id);
			        repository.save(empresa);
			      //return repository.save(empresa);
					SecurityContextHolder.getContext().getAuthentication();
			        return ResponseEntity.ok(empresa);
		    	});
	}
	
	
	
	
	@DeleteMapping("/rpc/empresas/{id}")
	void deleteEmpresa(@PathVariable int id) {
		repository.deleteById(id);
	}
	
}
