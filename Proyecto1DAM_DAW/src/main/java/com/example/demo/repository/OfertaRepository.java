package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Empresa;
import com.example.demo.model.Oferta;

public interface OfertaRepository extends JpaRepository<Oferta, Integer> {
	List<Oferta> findByEmpresa(Empresa empresa); //Busca Ofertas por Empresa
	List<Oferta> findByEmpresaIsNull();
}