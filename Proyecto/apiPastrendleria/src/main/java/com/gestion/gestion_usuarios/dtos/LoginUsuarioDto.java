package com.gestion.gestion_usuarios.dtos;

/**
 * Clase DTO (Data Transfer Object) que representa los datos de login de un usuario.
 * Esta clase es utilizada para transferir los datos necesarios para la autenticación
 * de un usuario, como su ID, email, contraseña y rol.
 */
public class LoginUsuarioDto {

    /** Identificador único del usuario. */
    private Long id;
    
    /** Email del usuario para la autenticación. */
    private String email;
    
    /** Contraseña para autenticar al usuario. */
    private String password;
    
    /** Rol del usuario (ej. "admin", "usuario", etc.). */
    private String rol;

    // ============================
    // Getters y Setters
    // ============================

    /**
     * Obtiene el ID del usuario.
     *
     * @return El ID del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param id El ID del usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el email del usuario.
     *
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     *
     * @param email El email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol El rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}
