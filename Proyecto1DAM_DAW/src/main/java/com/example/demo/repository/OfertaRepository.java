package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Empresa;
import com.example.demo.model.Oferta;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {
	List<Oferta> findByEmpresa(Empresa empresa); //Busca Ofertas por Empresa
	List<Oferta> findByEmpresaIsNull();

	//Tests
	//List<Oferta> findAll();
	//Optional<Oferta> findById();
}