package com.practica.ecommerce.spring_ecommerce.service.impl;

import com.practica.ecommerce.spring_ecommerce.model.Orden;
import com.practica.ecommerce.spring_ecommerce.repository.IOrdenRepository;
import com.practica.ecommerce.spring_ecommerce.service.IOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }
}
