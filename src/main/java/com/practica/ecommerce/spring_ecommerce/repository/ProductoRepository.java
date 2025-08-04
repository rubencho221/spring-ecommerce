package com.practica.ecommerce.spring_ecommerce.repository;

import com.practica.ecommerce.spring_ecommerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
