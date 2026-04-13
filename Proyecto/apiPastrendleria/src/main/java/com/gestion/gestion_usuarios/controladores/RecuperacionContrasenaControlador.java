package com.gestion.gestion_usuarios.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestion.gestion_usuarios.servicios.UsuarioServicio;

@RestController
@RequestMapping("/api/usuarios")
public class RecuperacionContrasenaControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Endpoint para restablecer la contraseña de un usuario mediante su email.
     * @param email el correo del usuario
     * @param nuevaPassword la nueva contraseña
     * @return respuesta indicando si la contraseña fue cambiada
     */
    @PostMapping("/recuperar-password")
    public ResponseEntity<String> recuperarPassword(
            @RequestParam("email") String email,
            @RequestParam("nuevaPassword") String nuevaPassword) {

        boolean actualizada = usuarioServicio.actualizarPasswordPorEmail(email, nuevaPassword);

        if (actualizada) {
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado con ese email.");
        }
    }
}
