package com.example.demo.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.example.demo.controller.CurriculumControladorRestful;
import com.example.demo.controller.EmpresaControladorRestful;

@Component
public class CurriculumModelAssembler implements RepresentationModelAssembler<Curriculum, EntityModel<Curriculum>> {

	@Override
	public EntityModel<Curriculum> toModel(Curriculum curriculum) {
		EntityModel<Curriculum> curriculumEntityModel = EntityModel.of(curriculum,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurriculumControladorRestful.class).readCurriculum(curriculum.getIdCurriculum())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurriculumControladorRestful.class).readCurriculums()).withRel("curriculums"));
		
		if (curriculum.getStatus() == Status.CV_SUBIDO) {
			curriculumEntityModel.add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(CurriculumControladorRestful.class)
					.validarCurriculum(curriculum.getIdCurriculum()))
					.withRel("cv validado"));
			
			curriculumEntityModel.add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(CurriculumControladorRestful.class)
					.invalidarCurriculum(curriculum.getIdCurriculum()))
					.withRel("cv invalidado"));
		}

		return curriculumEntityModel;
	}
}