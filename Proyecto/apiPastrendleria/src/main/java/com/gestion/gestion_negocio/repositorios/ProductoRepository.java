package com.gestion.gestion_negocio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.gestion_negocio.daos.ProductoDao;

/**
 * Repositorio que interactúa con la base de datos para realizar operaciones sobre productos.
 */
@Repository
public interface ProductoRepository extends JpaRepository<ProductoDao, Long> {
}
