package com.gestion.gestion_negocio.repositorios;

import com.gestion.gestion_negocio.daos.CarritoDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Carrito.
 * Proporciona métodos para acceder a los datos del carrito en la base de datos.
 */
@Repository
public interface CarritoRepository extends JpaRepository<CarritoDao, Long> {
    
    /**
     * Busca un carrito activo por ID de usuario.
     * @param idUsuario ID del usuario propietario del carrito
     * @return Optional con el carrito si existe, vacío si no
     */
    Optional<CarritoDao> findByIdUsuarioAndEstadoCarrito(Long idUsuario, String estadoCarrito);
    
    /**
     * Busca un carrito por ID de usuario (cualquier estado).
     * @param idUsuario ID del usuario propietario del carrito
     * @return Optional con el carrito si existe, vacío si no
     */
    Optional<CarritoDao> findByIdUsuario(Long idUsuario);
}
