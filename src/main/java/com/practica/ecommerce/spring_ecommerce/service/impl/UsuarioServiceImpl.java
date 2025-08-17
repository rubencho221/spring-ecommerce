package com.practica.ecommerce.spring_ecommerce.service.impl;

import com.practica.ecommerce.spring_ecommerce.model.Usuario;
import com.practica.ecommerce.spring_ecommerce.repository.IUsuarioRepository;
import com.practica.ecommerce.spring_ecommerce.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;


    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
