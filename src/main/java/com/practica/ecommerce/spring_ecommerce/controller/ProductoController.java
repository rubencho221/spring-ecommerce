package com.practica.ecommerce.spring_ecommerce.controller;


import com.practica.ecommerce.spring_ecommerce.model.Producto;


import com.practica.ecommerce.spring_ecommerce.model.Usuario;
import com.practica.ecommerce.spring_ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto) {
        LOGGER.info("Este es el objeto producto {}", producto);

        Usuario user = Usuario.builder()
                .id(1)
                .nombre("")
                .username("")
                .email("")
                .direccion("")
                .telefono("")
                .tipo("")
                .password("")
                .build();

        producto.setUsuario(user);
        productoService.save(producto);
        return "redirect:/productos";
    }


}
