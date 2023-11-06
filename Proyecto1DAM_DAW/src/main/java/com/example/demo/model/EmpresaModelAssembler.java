package com.example.demo.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.example.demo.controller.EmpresaControladorRestful;

@Component
public class EmpresaModelAssembler implements RepresentationModelAssembler<Empresa, EntityModel<Empresa>> {

	@Override
	public EntityModel<Empresa> toModel(Empresa empresa) {
		return EntityModel.of(empresa,
		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControladorRestful.class).readEmpresa(empresa.getIdEmpresa())).withSelfRel(),
		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControladorRestful.class).readEmpresas()).withRel("empresas"));
	}
}