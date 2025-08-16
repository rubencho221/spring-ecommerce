package com.practica.ecommerce.spring_ecommerce.service;

import com.practica.ecommerce.spring_ecommerce.model.Usuario;

import java.util.Optional;

public interface IUsuarioService {

    Optional<Usuario> findById(Integer id);

}