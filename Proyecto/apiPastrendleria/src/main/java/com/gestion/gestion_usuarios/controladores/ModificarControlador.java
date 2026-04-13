package com.gestion.gestion_usuarios.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gestion.gestion_usuarios.daos.UsuarioDao;
import com.gestion.gestion_usuarios.servicios.UsuarioServicio;

/**
 * Controlador que maneja las operaciones de modificación y consulta de usuarios y clubes.
 * Proporciona endpoints para modificar y buscar información específica.
 */
@RestController
@RequestMapping("/api/modificar")
public class ModificarControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Endpoint para modificar los datos de un usuario.
     *
     * @param idUsuario   ID del usuario a modificar.
     * @param nuevoNombre Nuevo nombre del usuario (opcional).
     * @param nuevoTelefono Nuevo teléfono del usuario (opcional).
     * @param nuevoRol    Nuevo rol del usuario (opcional).
     * @param nuevaFoto   Nueva foto del usuario (opcional).
     * @return una respuesta HTTP indicando el resultado de la operación.
     */
    @PutMapping(value = "/modificarUsuario/{idUsuario}", consumes = "multipart/form-data")
    public ResponseEntity<String> modificarUsuario(
            @PathVariable long idUsuario,
            @RequestParam(required = false) String nuevoNombre,
            @RequestParam(required = false) String nuevoTelefono,
            @RequestParam(required = false) String nuevoRol,
            @RequestPart(required = false) MultipartFile nuevaFoto) {

        System.out.println("Recibido idUsuario: " + idUsuario);
        System.out.println("Recibido nuevoNombre: " + nuevoNombre);
        System.out.println("Recibido nuevoTelefono: " + nuevoTelefono);
        System.out.println("Recibido nuevoRol: " + nuevoRol);
        System.out.println("Recibido nuevaFoto: " + (nuevaFoto != null ? nuevaFoto.getOriginalFilename() : "null"));

        byte[] nuevaFotoBytes = null;
        try {
            if (nuevaFoto != null && !nuevaFoto.isEmpty()) {
                nuevaFotoBytes = nuevaFoto.getBytes();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al procesar la foto");
        }

        boolean modificacionExitosa = usuarioServicio.modificarUsuario(idUsuario, nuevoNombre, nuevoTelefono, nuevoRol, nuevaFotoBytes);

        if (modificacionExitosa) {
            return ResponseEntity.ok("Usuario actualizado con éxito");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    /**
     * Endpoint para obtener información de un usuario por su ID.
     *
     * @param idUsuario ID del usuario a buscar.
     * @return una respuesta HTTP con el objeto {@link UsuarioDao} si se encuentra el usuario,
     *         o un código de estado 404 si no se encuentra.
     */
    @GetMapping("/buscarUsuario/{idUsuario}")
    public ResponseEntity<UsuarioDao> obtenerUsuario(@PathVariable long idUsuario) {
        UsuarioDao usuario = usuarioServicio.obtenerUsuarioPorId(idUsuario);

        if (usuario != null) {
            return ResponseEntity.ok(usuario); // Devuelve el usuario si lo encuentra
        } else {
            return ResponseEntity.status(404).body(null); // Retorna un 404 si no encuentra el usuario
        }
    }

   
}
