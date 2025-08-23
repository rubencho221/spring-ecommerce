package com.practica.ecommerce.spring_ecommerce.controller;

import com.practica.ecommerce.spring_ecommerce.model.DetalleOrden;
import com.practica.ecommerce.spring_ecommerce.model.Orden;
import com.practica.ecommerce.spring_ecommerce.model.Producto;
import com.practica.ecommerce.spring_ecommerce.model.Usuario;
import com.practica.ecommerce.spring_ecommerce.service.IDetalleOrdenService;
import com.practica.ecommerce.spring_ecommerce.service.IOrdenService;
import com.practica.ecommerce.spring_ecommerce.service.IUsuarioService;
import com.practica.ecommerce.spring_ecommerce.service.IProductoService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    private IDetalleOrdenService detalleOrdenService;

    // Lista que almacena los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    // Datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model, HttpSession session) {

        log.info("Sesion del usuario: {}", session.getAttribute("idusuario"));

        model.addAttribute("productos", productoService.findAll());

        // Session
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model) {

        log.info("Id producto enviado como parametro {}", id);

        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto", producto);

        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, Integer cantidad, Model model) {

        Producto producto = productoService.get(id).orElse(null);
        if (producto == null) {
            log.warn("Producto con ID {} no encontrado", id);
            return "redirect:/";
        }

        // Verificar si el producto ya está en el carrito
        Optional<DetalleOrden> existente = detalles.stream()
                .filter(p -> p.getProducto().getId().equals(id))
                .findFirst();

        if (existente.isPresent()) {
            // Si ya existe, actualizamos la cantidad y el total
            DetalleOrden detalleExistente = existente.get();
            detalleExistente.setCantidad(detalleExistente.getCantidad() + cantidad);
            detalleExistente.setTotal(detalleExistente.getCantidad() * detalleExistente.getPrecio());
        } else {
            // Si no existe, lo agregamos nuevo
            DetalleOrden detalleOrden = new DetalleOrden();
            detalleOrden.setCantidad(cantidad);
            detalleOrden.setPrecio(producto.getPrecio());
            detalleOrden.setNombre(producto.getNombre());
            detalleOrden.setTotal(producto.getPrecio() * cantidad);
            detalleOrden.setProducto(producto);
            detalles.add(detalleOrden);
        }

        // Recalcular el total de la orden
        double sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    // Quitar producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model) {

        // Lista nueva de productos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenesNueva.add(detalleOrden);
            }
        }

        // Poner la nueva lista con los productos restantes
        detalles = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session) {
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        // Sesion
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        return "/usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session) {

        Usuario usuario = usuarioService.findById( Integer.parseInt(session.getAttribute("idusuario").toString()) ).get();

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return "usuario/resumenorden";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session) {
        Date fechaCreacion = new Date();
        Orden nuevaOrden = new Orden();
        nuevaOrden.setFechaCreacion(fechaCreacion);
        nuevaOrden.setNumero(ordenService.generarNumeroOrden());

        Integer idUsuario = Integer.parseInt(session.getAttribute("idusuario").toString());
        Usuario usuario = usuarioService.findById(idUsuario).orElse(null);
        if (usuario == null) {
            log.warn("Usuario no encontrado con ID: {}", idUsuario);
            return "redirect:/error";
        }
        nuevaOrden.setUsuario(usuario);
        nuevaOrden.setTotal(orden.getTotal());

        // Guardar orden primero
        ordenService.save(nuevaOrden);

        // Guardar detalles de la orden
        for (DetalleOrden dt : detalles) {
            dt.setOrden(nuevaOrden);
            detalleOrdenService.save(dt);
        }

        // Limpiar carrito y orden en memoria
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model) {
        log.info("Nombre del producto: {}", nombre);

        // Normaliza el nombre ingresado a minúsculas
        String nombreProductoNormalizado = nombre.toLowerCase();

        // Obtiene la lista de Productos y filtra por nombre
        // El Filtro lo realiza ignorando mayúsculas/minúsculas
        List<Producto> productos = productoService.findAll().stream()
                .filter(p -> p.getNombre() != null && p.getNombre().toLowerCase().contains(nombreProductoNormalizado))
                .collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "usuario/home";
    }

}
