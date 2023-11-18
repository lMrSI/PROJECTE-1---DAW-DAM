package com.example.demo.model;
import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
@Entity
@Table(name="ofertas")
public class Oferta {
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idEmpresa")
	@OnDelete(action = OnDeleteAction.CASCADE)
	
	//IGNORA EMPRESA DE LAS OFERTAS y evita bucle --> NO MUESTRA EMPRESA
	@JsonIgnore
	//IGNORA ATRIBUTO OFERTA DE LAS EMPRESAS y evita bucle --> MANEJA EMPRESAS SIN OFERTAS(tambien con idEmpresa)
	//@JsonIgnoreProperties("ofertas")
	private Empresa empresa; //clave foranea
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOferta;
	private String descripcion;
	private String funciones;
	private String tipoContrato;
	private String titulo;

	//private String ubicacion;




	public Oferta() {}

	public int getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(int idOferta) {
		this.idOferta = idOferta;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getFunciones() {
		return funciones;
	}

	public void setFunciones(String funciones) {
		this.funciones = funciones;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {

		return Objects.hash(descripcion, empresa, funciones, idOferta, tipoContrato, titulo);

	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Oferta other = (Oferta) o;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(empresa, other.empresa)
				&& Objects.equals(funciones, other.funciones) && idOferta == other.idOferta
				&& Objects.equals(tipoContrato, other.tipoContrato) && Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return "String";
	}
	
	
	
	
}
