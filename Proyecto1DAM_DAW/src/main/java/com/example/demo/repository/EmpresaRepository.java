package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Empresa;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
	Empresa findTopByOrderByIdEmpresaDesc();  //Busca la ultima oferta

	//Para Tests
	List<Empresa> findAllByTamaño(String tamaño);
	List<Empresa> findAllByUbicacion(String ubicacion);

}