package com.gestion.gestion_usuarios.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.gestion_usuarios.servicios.UsuarioServicio;

@RestController
public class ValidacionEmailControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Endpoint para verificar si un email ya está registrado.
     * @param email Email a verificar.
     * @return true si existe el email, false si no existe.
     */
    @GetMapping("/api/usuarios/existe")
    public ResponseEntity<Boolean> existeEmail(@RequestParam("email") String email) {
        boolean existe = usuarioServicio.emailExistsUsuario(email);
        return ResponseEntity.ok(existe);
    }
}
