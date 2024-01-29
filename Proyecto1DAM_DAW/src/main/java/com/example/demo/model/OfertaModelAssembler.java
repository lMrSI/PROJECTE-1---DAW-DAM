package com.example.demo.model;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;
import com.example.demo.controller.OfertaControladorRestful;

@Component
public class OfertaModelAssembler implements RepresentationModelAssembler<Oferta, EntityModel<Oferta>> {
	@Override
	public EntityModel<Oferta> toModel(Oferta oferta) {
		return EntityModel.of(oferta,
		linkTo(
				methodOn(OfertaControladorRestful.class)
				.readOferta(oferta.getIdOferta())
		).withSelfRel(),
		linkTo(
				methodOn(OfertaControladorRestful.class)
				.readOfertas()
		).withRel("ofertas"));
	}
}