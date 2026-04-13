package com.gestion.gestion_usuarios.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.gestion_usuarios.dtos.RegistroUsuarioDto;
import com.gestion.gestion_usuarios.servicios.UsuarioServicio;

/**
 * Controlador que maneja el registro de usuarios
 * Proporciona endpoints para registrar nuevos usuarios y clubes en el sistema.
 */
@RestController
@RequestMapping("/api/registro")
public class RegistroControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param usuarioDto Objeto que contiene la información del usuario a registrar.
     * @return Una respuesta HTTP con el resultado del registro:
     *         <ul>
     *         <li>201 (CREATED): Usuario registrado exitosamente.</li>
     *         <li>400 (BAD REQUEST): El email del usuario es obligatorio.</li>
     *         <li>409 (CONFLICT): El email ya está registrado.</li>
     *         <li>500 (INTERNAL SERVER ERROR): Error interno del servidor.</li>
     *         </ul>
     */
    @PostMapping("/usuario")
    public ResponseEntity<String> registroUsuario(@RequestBody RegistroUsuarioDto usuarioDto) {
        try {
            if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email es obligatorio.");
            }

            if (usuarioServicio.emailExistsUsuario(usuarioDto.getEmailUsuario())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está registrado.");
            }

            usuarioServicio.registroUsuario(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

}
