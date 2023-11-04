package com.example.demo.model;

import java.util.ArrayList;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

@Entity
@Table(name="empresas") //PDF
public class Empresa {
	
	//private @Id @GeneratedValue int id; //TUTORIAL
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEmpresa; //PDF
	private String nombre;
	private String sector;
	private String tamaño;
	private String tipo;
	private String ubicacion;
	
	@OneToMany(mappedBy = "empresa")
	private ArrayList<Oferta> ofertas;

	public Empresa() {}
	//tutorial
	/*
	Empresa(String nombre, String tipo, String sector, String tamaño, String ubicacion, ArrayList<String> ofertas) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.sector = sector;
		this.tamaño = tamaño;
		this.ubicacion = ubicacion;
		this.ofertas = ofertas;
	} */



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

	public ArrayList<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(ArrayList<Oferta> ofertas) {
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
			return Objects.hash(this.idEmpresa, this.nombre, this.tipo, this.sector, this.tamaño, this.ubicacion, 
					this.ofertas);
	}	

	@Override
	public String toString() {
			return "String";
	}
}