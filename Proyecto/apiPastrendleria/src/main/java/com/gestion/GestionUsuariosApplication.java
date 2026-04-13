package com.gestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal para la aplicación de gestión de usuarios.
 * Esta clase arranca la aplicación Spring Boot.
 */
@SpringBootApplication
public class GestionUsuariosApplication {

    /**
     * Método principal de la aplicación.
     * Este método inicia la ejecución de la aplicación utilizando Spring Boot.
     *
     * @param args los argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(GestionUsuariosApplication.class, args);
    }
}
