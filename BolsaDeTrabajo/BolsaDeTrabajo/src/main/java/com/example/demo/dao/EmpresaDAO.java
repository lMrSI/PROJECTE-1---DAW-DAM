package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Empresa;

public interface EmpresaDAO extends JpaRepository<Empresa, Long>{
	
}
