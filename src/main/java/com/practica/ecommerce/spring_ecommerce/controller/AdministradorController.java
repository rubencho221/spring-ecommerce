package com.practica.ecommerce.spring_ecommerce.controller;

import com.practica.ecommerce.spring_ecommerce.model.Orden;
import com.practica.ecommerce.spring_ecommerce.model.Producto;
import com.practica.ecommerce.spring_ecommerce.service.IOrdenService;
import com.practica.ecommerce.spring_ecommerce.service.IProductoService;
import com.practica.ecommerce.spring_ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    private Logger logg = LoggerFactory.getLogger(AdministradorController.class);

    @GetMapping("")
    public String home(Model model) {

        List<Producto> productos = productoService.findAll();
        model.addAttribute("productos", productos);

        return "administrador/home";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {

        model.addAttribute("usuarios", usuarioService.findAll());
        return "administrador/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model) {

        model.addAttribute("ordenes", ordenService.findAll());
        return "administrador/ordenes";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(Model model, @PathVariable Integer id) {

        logg.info("ID de la Orden {}", id);

        Orden orden = ordenService.findById(id).get();

        model.addAttribute("detalles", orden.getDetalles());

        return "administrador/detalleorden";
    }

}
