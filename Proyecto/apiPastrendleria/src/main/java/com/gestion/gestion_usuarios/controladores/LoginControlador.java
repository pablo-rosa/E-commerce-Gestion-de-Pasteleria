package com.gestion.gestion_usuarios.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion.gestion_usuarios.dtos.LoginUsuarioDto;
import com.gestion.gestion_usuarios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar el proceso de autenticación de usuarios y clubes.
 * Define endpoints relacionados con la validación de credenciales.
 */
@RestController
@RequestMapping("/api/login")
public class LoginControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Endpoint para validar usuario.
     * <p>
     * Recibe un DTO con email y contraseña, valida las credenciales,
     * y devuelve un JSON con id y rol si son correctas.
     * </p>
     *
     * @param usuario DTO con email y contraseña
     * @return ResponseEntity con JSON { "id": 12, "rol": "usuario" } o error
     */
    @PostMapping("/validarUsuario")
    public ResponseEntity<String> autenticarUsuario(@RequestBody LoginUsuarioDto usuario) {
        System.out.println("Email recibido: " + usuario.getEmail());
        System.out.println("Contraseña recibida: " + usuario.getPassword());

        // Llamamos al servicio para validar las credenciales
        ResponseEntity<String> resultado = usuarioServicio.validarCredenciales(usuario.getEmail(), usuario.getPassword());

        // Verificamos si las credenciales son correctas
        if (resultado.getStatusCodeValue() == 401) {
            System.out.println("Credenciales incorrectas");
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos.");
        }

        try {
            // Obtenemos la respuesta del servicio
            String cuerpo = resultado.getBody().trim();
            System.out.println("Respuesta cruda del servicio: " + cuerpo);

            // Suponiendo que validarCredenciales ahora devuelve "id;rol"
            String[] partes = cuerpo.split(";");
            if (partes.length != 2) {
                System.out.println("ERROR: Respuesta de credenciales mal formada: " + cuerpo);
                return ResponseEntity.status(500).body("Error interno en la autenticación.");
            }

            Long idUsuario = Long.parseLong(partes[0]);
            String rol = partes[1];

            System.out.println("ID del usuario: " + idUsuario);
            System.out.println("Rol del usuario: " + rol);

            // Creamos un JSON manualmente
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(new UsuarioLoginResponse(idUsuario, rol));

            return ResponseEntity.ok(jsonResponse);

        } catch (Exception e) {
            System.out.println("ERROR: Exception al procesar la autenticación: " + e);
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno en la autenticación.");
        }
    }

    /**
     * Clase interna para representar la respuesta JSON de login
     */
    private static class UsuarioLoginResponse {
        private Long id;
        private String rol;

        public UsuarioLoginResponse(Long id, String rol) {
            this.id = id;
            this.rol = rol;
        }

        public Long getId() {
            return id;
        }

        public String getRol() {
            return rol;
        }
    }
}
