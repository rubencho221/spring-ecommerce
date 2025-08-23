package com.practica.ecommerce.spring_ecommerce.repository;

import com.practica.ecommerce.spring_ecommerce.model.Orden;
import com.practica.ecommerce.spring_ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {
    List<Orden> findByUsuario (Usuario usuario);
}
