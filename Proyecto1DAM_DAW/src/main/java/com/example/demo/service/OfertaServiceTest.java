package com.example.demo.service;

import com.example.demo.model.Empresa;
import com.example.demo.model.Oferta;
import com.example.demo.repository.OfertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OfertaServiceTest implements OfertaInterfaceTest {
    private OfertaRepository repositoryOferta;
    public OfertaServiceTest(OfertaRepository repositoryOferta) {
        this.repositoryOferta = repositoryOferta;
    }

    /*
    @Override
    public List<Oferta> findByEmpresa(Empresa empresa) {
        return this.repositoryOferta.findByEmpresa(empresa);
    };*/

    /*
    @Override
    public List<Oferta> findByEmpresaIsNull() {
        return this.repositoryOferta.findByEmpresaIsNull();
    }*/

    @Override
    public List<Oferta> findAll() {
        return this.repositoryOferta.findAll();
    }

    @Override
    public Optional<Oferta> findById(int idOferta) {
        return this.repositoryOferta.findById(idOferta);
    }

}
