package com.example.demo.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.example.demo.controller.EmpresaControladorRestful;
import com.example.demo.controller.OfertaControladorRestful;

@Component
public class EmpresaModelAssembler implements RepresentationModelAssembler<Empresa, EntityModel<Empresa>> {
    private final OfertaModelAssembler ofertaModelAssembler;

    public EmpresaModelAssembler(OfertaModelAssembler ofertaModelAssembler) {
        this.ofertaModelAssembler = ofertaModelAssembler;
    }
    
	@Override
	public EntityModel<Empresa> toModel(Empresa empresa) {
//		return EntityModel.of(empresa,
//		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControladorRestful.class).readEmpresa(empresa.getIdEmpresa())).withSelfRel(),
//		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControladorRestful.class).readEmpresas()).withRel("empresas"));
		List<EntityModel<Oferta>> ofertasModel = empresa.getOfertas()
                .stream()
                .map(ofertaModelAssembler::toModel)
                .collect(Collectors.toList());
        EntityModel<Empresa> empresaModel = EntityModel
                .of(empresa,
                linkTo(
                        methodOn(EmpresaControladorRestful.class)
                        .readEmpresa(empresa.getIdEmpresa())
                ).withSelfRel(),
                linkTo(
                        methodOn(EmpresaControladorRestful.class)
                        .readEmpresas()
                ).withRel("empresas"),
                linkTo(
                        methodOn(OfertaControladorRestful.class)
                        .readOfertasByIdEmpresa(empresa.getIdEmpresa())
                ).withRel("ofertas de empresa: " + empresa.getNombre())); // Enlace a las ofertas de la empresa

        
        
        return empresaModel;
	}

    public EntityModel<Empresa> tooModel(Empresa empresa) {
        return EntityModel.of(empresa,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControladorRestful.class).readEmpresa(empresa.getIdEmpresa())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControladorRestful.class).readEmpresas()).withRel("empresas"));
    }
}