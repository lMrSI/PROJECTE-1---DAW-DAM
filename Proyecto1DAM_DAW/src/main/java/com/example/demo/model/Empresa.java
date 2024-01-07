package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;


@Entity
@Table(name="empresas")
public class Empresa {
	@Schema(example = "1", description = "identificador de empresa")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEmpresa;

	@Schema(example = "Google", description = "Nombre de la empresa")
	private String nombre;

	@Schema(example = "Telecomunicaciones", description = "Indica actividad laboral de la empresa")
	private String sector;

	@Schema(example = "Grande", description = "Indica el volumen de trabajadores")
	private String tamaño;

	@Schema(example = "S.A", description = "Indica el tipo de sociedad de la empresa")
	private String tipo;

	@Schema(example = "Barcelona - Poblenou", description = "Indica la localidad i el barrio de las oficinas de la empresa")
	private String ubicacion;
	
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL, orphanRemoval = true) //PERSIST , MERGE , REMOVE , REFRESH , DETACH | targetEntity=Empresa
	//IGNORA OFERTAS DE LA EMPRESA y evita bucle --> NO MANEJA OFERTAS
	//@JsonIgnore
	//IGNORA ATRIBUTO EMPRESA DE LAS OFERTAS y evita bucle --> MANEJA OFERTAS SIN EMPRESA
	@JsonIgnoreProperties("empresa")
	//@JsonSerialize(as = ArrayList.class)
	private List<Oferta> ofertas;
	public Empresa() {}

	public Empresa(String nombre, String sector, String tamaño, String tipo, String ubicacion, List<Oferta> ofertas) {
		this.nombre = nombre;
		this.sector = sector;
		this.tamaño = tamaño;
		this.tipo = tipo;
		this.ubicacion = ubicacion;
		this.ofertas = ofertas;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getTamaño() {
		return tamaño;
	}

	public void setTamaño(String tamaño) {
		this.tamaño = tamaño;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Oferta> getOfertas() {
		return ofertas;
	}


	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Empresa))
			return false;

		Empresa empresa = (Empresa) o;
		return Objects.equals(this.idEmpresa, empresa.idEmpresa) && Objects.equals(this.nombre, empresa.nombre)
			   && Objects.equals(this.tipo, empresa.tipo) && Objects.equals(this.sector, empresa.sector)
			   && Objects.equals(this.tamaño, empresa.tamaño) && Objects.equals(this.ubicacion, empresa.ubicacion)
			   && Objects.equals(this.ofertas, empresa.ofertas);
  }

	@Override
	public int hashCode() {
			return Objects.hash(this.idEmpresa, this.nombre, this.ofertas, this.tipo, this.sector, this.tamaño, this.ubicacion);
	}

	
	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", nombre=" + nombre + ", ofertas=" + ofertas + ", sector=" + sector
				+ ", tamaño=" + tamaño + ", tipo=" + tipo + ", ubicacion=" + ubicacion + "]";
	}	

}