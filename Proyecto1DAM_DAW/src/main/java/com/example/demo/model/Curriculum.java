package com.example.demo.model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="curriculums")
public class Curriculum {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCurriculum;
	private int idAlumno;
	private Status status;
	
	public Curriculum() {}

	public int getIdCurriculum() {
		return idCurriculum;
	}

	public void setIdCurriculum(int idCurriculum) {
		this.idCurriculum = idCurriculum;
	}

	public int getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAlumno, idCurriculum, status);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Curriculum curriculum = (Curriculum) o;
		return Objects.equals(this.idCurriculum, curriculum.idCurriculum) && Objects.equals(this.idAlumno, curriculum.idAlumno)
		        && this.status == curriculum.status;
	}

	@Override
	public String toString() {
		return "Curriculum [idCurriculum=" + idCurriculum + ", idAlumno=" + idAlumno + ", status=" + status + "]";
	}
	
	
	
	
	
	
}
