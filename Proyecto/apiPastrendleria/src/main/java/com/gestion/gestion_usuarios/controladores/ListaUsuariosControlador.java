package com.gestion.gestion_usuarios.controladores;

import com.gestion.gestion_usuarios.dtos.ListaUsuariosDto;
import com.gestion.gestion_usuarios.servicios.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con la visualización
 * de la lista de usuarios. Expone un endpoint para obtener información pública
 * de todos los usuarios registrados en el sistema.
 */
@RestController
@RequestMapping("/api/usuarios")
public class ListaUsuariosControlador {

    // Servicio que contiene la lógica de negocio relacionada con usuarios
    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Obtiene una lista de todos los usuarios del sistema, sin incluir datos sensibles.
     *
     * @return Lista de usuarios representados por ListaUsuariosDto
     */
    @GetMapping("/lista")
    public List<ListaUsuariosDto> obtenerTodosLosUsuarios() {
        return usuarioServicio.obtenerTodosLosUsuarios();
    }
}
