package com.example.demo.model;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ofertas")
public class Oferta {
	@Schema(example = "1", description = "Datos de la empresa a la que pertence")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idEmpresa")
	@OnDelete(action = OnDeleteAction.CASCADE)
	//IGNORA EMPRESA DE LAS OFERTAS y evita bucle --> NO MUESTRA EMPRESA
	//@JsonIgnore
	//IGNORA ATRIBUTO OFERTA DE LAS EMPRESAS y evita bucle --> MANEJA EMPRESAS SIN OFERTAS(tambien con idEmpresa)
	@JsonIgnoreProperties("ofertas")
	private Empresa empresa; //clave foranea



	@Schema(example = "1", description = "Identificador de la oferta")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOferta;

	@Schema(example = "Esta es la descripcion de las condiciones de la oferta",
			description = "Explicacion e introduccion de la oferta")
	private String descripcion;
	@Schema(example = "Mantenimiento de red, de web, ...",
			description = "Indica una lista de principales tareas del cargo")
	private String funciones;

	@Schema(example = "DUAL", description = "Indica el tipo de convenio de las practicas: FCT o DUAL")
	private String tipoContrato;
	@Schema(example = "Tecnico informatico", description = "Indica un perfil del cargo de la oferta")
	private String titulo;


	@OneToMany(mappedBy ="oferta", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("oferta")
	private List<Usuario> usuarios;

	public Oferta() {}

	public Oferta(Empresa empresa, int idOferta, String descripcion, String funciones, String tipoContrato, String titulo, List<Usuario> usuarios) {
		this.empresa = empresa;
		this.idOferta = idOferta;
		this.descripcion = descripcion;
		this.funciones = funciones;
		this.tipoContrato = tipoContrato;
		this.titulo = titulo;
		this.usuarios = usuarios;
	}

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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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
