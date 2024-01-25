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
	// P R O P I E D A D E S
	@Schema(example = "1", description = "identificador de empresa")
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEmpresa;
	@Schema(example = "Caixabank Tech", description = "Nombre de la empresa")
	private String nombre;
	@Schema(example = "S.L.U", description = "Tipo de sociedad de la empresa")
	private String tipo;
	@Schema(example = "Finanzas", description = "Sector de la empresa")
	private String sector;
	@Schema(example = "Grande", description = "Volumen de trabajadores")
	private String tamaño;
	@Schema(example = "Sabino Arana, 54, Les Corts, 08028 Barcelona", description = "dirección de sede")
	private String ubicacion;
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"idOferta", "empresa", "usuarios", "titulo", "descripcion", "funciones", "tipoContrato"})
	private List<Oferta> ofertas;


	//C O N S T R U C T O R
	public Empresa() {}
	public Empresa(String nombre, String sector, String tamaño, String tipo, String ubicacion, List<Oferta> ofertas) {
		this.nombre = nombre;
		this.sector = sector;
		this.tamaño = tamaño;
		this.tipo = tipo;
		this.ubicacion = ubicacion;
		this.ofertas = ofertas;
	}


	//G E T T E R S   Y   S E T T E R S
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


	//O T R O S
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
		return "Empresa{" +
				"idEmpresa=" + idEmpresa +
				", nombre='" + nombre + '\'' +
				", tipo='" + tipo + '\'' +
				", sector='" + sector + '\'' +
				", tamaño='" + tamaño + '\'' +
				", ubicacion='" + ubicacion + '\'' +
				", ofertas=" + ofertas +
				'}';
	}
}