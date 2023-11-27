package com.example.demo.service;
import com.example.demo.model.Empresa;
import com.example.demo.model.Oferta;
import java.util.List;
import java.util.Optional;

public interface OfertaInterfaceTest {
	//public List<Oferta> findByEmpresa(Empresa empresa);
	//public List<Oferta> findByEmpresaIsNull();

	//Tests
	public List<Oferta> findAll();
	Optional<Oferta> findById(int idOferta);
}