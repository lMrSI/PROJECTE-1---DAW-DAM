package com.example.demo.controller;

import com.example.demo.model.Oferta;
import com.example.demo.repository.OfertaRepository;
import com.example.demo.service.OfertaServiceTest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OfertaControladorTest {
    private OfertaServiceTest ofertaService;

    public OfertaControladorTest(OfertaServiceTest ofertaService) {
        this.ofertaService = ofertaService;
    }

    @GetMapping("/tests/ofertas")
    public String getOfertas(Model model) {
        model.addAttribute("empresas", ofertaService.findAll());
        return "lista empresas";
    }

    @GetMapping("/tests/ofertas/{idOferta}")
    public String getOferta(Model model, @PathVariable int idOferta) {
        Oferta oferta = ofertaService.findById(idOferta).orElseThrow();
        model.addAttribute("empresa", oferta);
        return "empresa";
    }
}