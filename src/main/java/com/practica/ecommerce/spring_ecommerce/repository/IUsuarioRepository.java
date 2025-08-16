package com.practica.ecommerce.spring_ecommerce.repository;

import com.practica.ecommerce.spring_ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}
