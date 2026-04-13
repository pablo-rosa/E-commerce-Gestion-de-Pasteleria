package com.gestion.gestion_usuarios.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración para la seguridad de la aplicación.
 * <p>
 * En esta clase se define el comportamiento para la codificación y verificación de contraseñas en la aplicación
 * utilizando el algoritmo de hash BCrypt, proporcionado por Spring Security.
 * </p>
 */
@Configuration
public class SecurityConfig {

    /**
     * Método que configura un bean de tipo PasswordEncoder.
     * <p>
     * El PasswordEncoder se utiliza para codificar contraseñas de forma segura antes de almacenarlas en la base de datos.
     * En este caso, se utiliza BCryptPasswordEncoder, que es un algoritmo de hash seguro para contraseñas, 
     * el cual también incluye un "salting" para proteger contra ataques de diccionario y rainbow tables.
     * </p>
     * @return un PasswordEncoder configurado con el algoritmo BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Se retorna una instancia de BCryptPasswordEncoder que proporcionará un hashing seguro de contraseñas
        return new BCryptPasswordEncoder();
    }
}
