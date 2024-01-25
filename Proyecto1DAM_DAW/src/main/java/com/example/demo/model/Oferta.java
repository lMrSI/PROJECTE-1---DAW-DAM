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
	// P R O P I E D A D E S
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "idEmpresa") @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Empresa empresa;
	@Schema(example = "1", description = "Identificador de la oferta")
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOferta;
	@Schema(example = "Programador Junior Java", description = "Título")
	private String titulo;
	@Schema(example = "Desarollo de aplicaciones multiplataforma", description = "descripción de la oferta")
	private String descripcion;
	@Schema(example = "Analisís de código con SpringBoot", description = "Tareas de la oferta")
	private String funciones;
	@Schema(example = "FCT", description = "Tipo de prácticas")
	private String tipoContrato;
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "oferta_usurios", joinColumns = @JoinColumn(name = "id_oferta"), inverseJoinColumns = @JoinColumn(name = "id_usuario"))
	@JsonIgnore
	private List<Usuario> usuarios;


	//C O N S T R U C T O R
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


	//G E T T E R S   Y   S E T T E R S
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


	// O T R O S
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
		return "Oferta{" +
				"empresa=" + empresa +
				", idOferta=" + idOferta +
				", titulo='" + titulo + '\'' +
				", descripcion='" + descripcion + '\'' +
				", funciones='" + funciones + '\'' +
				", tipoContrato='" + tipoContrato + '\'' +
				", usuarios=" + usuarios +
				'}';
	}
}
