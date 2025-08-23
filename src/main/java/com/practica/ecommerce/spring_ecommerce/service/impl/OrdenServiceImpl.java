package com.practica.ecommerce.spring_ecommerce.service.impl;

import com.practica.ecommerce.spring_ecommerce.model.Orden;
import com.practica.ecommerce.spring_ecommerce.model.Usuario;
import com.practica.ecommerce.spring_ecommerce.repository.IOrdenRepository;
import com.practica.ecommerce.spring_ecommerce.service.IOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    @Override
    public Optional<Orden> findById(Integer id) {
        return ordenRepository.findById(id);
    }

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public String generarNumeroOrden() {
        int numero = 0;
        String numeroConcatenado = "";

        List<Orden> ordenes = findAll();
        List<Integer> numeros = new ArrayList<Integer>();

        ordenes.stream().forEach(o -> numeros.add( Integer.parseInt( o.getNumero() )));

        if (ordenes.isEmpty()) {
            numero = 1;
        } else {
            // Obtenemos el ultimo numero de la lista de ordenes
            numero = numeros.stream().max(Integer::compare).get();
            numero ++;
        }

        numeroConcatenado = String.format("%010d", numero);

        return numeroConcatenado;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }
}
