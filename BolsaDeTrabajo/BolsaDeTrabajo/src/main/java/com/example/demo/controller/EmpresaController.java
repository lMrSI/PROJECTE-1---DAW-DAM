package com.example.demo.model.controller;

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

import com.example.demo.dao.EmpresaDAO;
import com.example.demo.model.Empresa;
import com.example.demo.repository.EmpresaRepository;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;
/*
    @GetMapping
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }
*/  
    @GetMapping
    public ResponseEntity<List<Empresa>> getEmpresa() {
        List<Empresa> empresas = empresaRepository.findAll();
        return ResponseEntity.ok(empresas);
    }
/*
    @GetMapping("/{id}")
    public Empresa consultarEmpresa(@PathVariable Long id) {
        return empresaRepository.findById(id).orElse(null);
    }
*/
    @RequestMapping(value="{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable("id") Long id){
    	Optional<Empresa> optionalEmpresa= empresaRepository.findById(id);
    	if(optionalEmpresa.isPresent()) {
    		return ResponseEntity.ok(optionalEmpresa.get());
    	}else {
    		return ResponseEntity.noContent().build();    	}
    }
/*
    @PostMapping
    public Empresa agregarEmpresa(@RequestBody Empresa empresa) {
        return empresaRepository.save(empresa);
    }
*/
    @PostMapping
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa) {
        Empresa newEmpresa = empresaRepository.save(empresa);
        return ResponseEntity.ok(newEmpresa);
    }
/*
    @PutMapping("/{id}")
    public Empresa modificarEmpresa(@PathVariable Long id, @RequestBody Empresa empresaActualizada) {
        Empresa empresa = empresaRepository.findById(id).orElse(null);
        if (empresa != null) {
            empresa.setNombre(empresaActualizada.getNombre());
            empresa.setDescripcion(empresaActualizada.getDescripcion());
            return empresaRepository.save(empresa);
        }
        return null;
    }
*/
    @PutMapping
    public ResponseEntity<Empresa> updateEmpresa(@RequestBody Empresa empresa){
    	Optional<Empresa> optionalEmpresa = empresaRepository.findById(empresa.getId());
    	if(optionalEmpresa.isPresent()) {
    		Empresa actualizarEmpresa = optionalEmpresa.get();
    		actualizarEmpresa.setNombre(empresa.getNombre());
    		empresaRepository.save(actualizarEmpresa);
    		return ResponseEntity.ok(actualizarEmpresa);
    	}else {
    		return ResponseEntity.notFound().build();
    	}
    }
/*
    @DeleteMapping("/{id}")
    public void eliminarEmpresa(@PathVariable Long id) {
        empresaRepository.deleteById(id);
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable("id") Long id){
    	empresaRepository.deleteById(id);
    	return ResponseEntity.ok(null);
    }
}