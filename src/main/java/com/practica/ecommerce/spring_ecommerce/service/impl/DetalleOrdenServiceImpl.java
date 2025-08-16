package com.practica.ecommerce.spring_ecommerce.service.impl;

import com.practica.ecommerce.spring_ecommerce.model.DetalleOrden;
import com.practica.ecommerce.spring_ecommerce.repository.IDetalleOrdenRepository;
import com.practica.ecommerce.spring_ecommerce.service.IDetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService {

    @Autowired
    IDetalleOrdenRepository detalleOrdenRepository;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
}
