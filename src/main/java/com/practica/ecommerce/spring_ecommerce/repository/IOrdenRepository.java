package com.practica.ecommerce.spring_ecommerce.repository;

import com.practica.ecommerce.spring_ecommerce.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {
}
