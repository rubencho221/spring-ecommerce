package com.practica.ecommerce.spring_ecommerce.service;

import com.practica.ecommerce.spring_ecommerce.model.Orden;
import com.practica.ecommerce.spring_ecommerce.model.Usuario;

import java.util.List;

public interface IOrdenService {
    List<Orden> findAll();
    Orden save (Orden orden);
    String generarNumeroOrden();
    List<Orden> findByUsuario (Usuario usuario);
}
