package com.example.demo.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping("/{id}")
    public Empresa consultarEmpresa(@PathVariable Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Empresa agregarEmpresa(@RequestBody Empresa empresa) {
        return empresaRepository.save(empresa);
    }

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

    @DeleteMapping("/{id}")
    public void eliminarEmpresa(@PathVariable Long id) {
        empresaRepository.deleteById(id);
    }

    @GetMapping
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }
}