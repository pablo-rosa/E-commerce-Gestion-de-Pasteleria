package com.gestion.gestion_usuarios.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.gestion_usuarios.daos.UsuarioDao;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioDao, Long> {

    /**
     * Busca un usuario por su email.
     * 
     * @param emailUsuario el email del usuario.
     * @return el usuario encontrado con ese email.
     */
    UsuarioDao findByEmailUsuario(String emailUsuario);

    /**
     * Verifica si un usuario con el email dado ya existe en la base de datos.
     * 
     * @param emailUsuario el email del usuario.
     * @return true si el usuario con ese email existe, false en caso contrario.
     */
    boolean existsByEmailUsuario(String emailUsuario);

    /**
     * Busca un usuario por su ID.
     * 
     * @param id el ID del usuario.
     * @return un Optional que contiene el usuario encontrado, o vacío si no se encuentra.
     */
    Optional<UsuarioDao> findById(Long id); 
}
