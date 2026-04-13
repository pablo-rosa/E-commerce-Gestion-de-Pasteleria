package com.gestion.gestion_negocio.dtos;

import java.util.List;

/**
 * DTO para añadir o actualizar un carrito.
 * Se utiliza para recibir datos del cliente cuando se añade o modifica un carrito.
 */
public class AñadirCarritoDto {
    
    private Long idUsuario;
    private String direccionCarrito;
    private List<AñadirDetalleCarritoDto> detalles;

    /**
     * Constructor por defecto.
     */
    public AñadirCarritoDto() {}

    // ========== GETTERS ==========

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getDireccionCarrito() {
        return direccionCarrito;
    }

    public List<AñadirDetalleCarritoDto> getDetalles() {
        return detalles;
    }

    // ========== SETTERS ==========

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setDireccionCarrito(String direccionCarrito) {
        this.direccionCarrito = direccionCarrito;
    }

    public void setDetalles(List<AñadirDetalleCarritoDto> detalles) {
        this.detalles = detalles;
    }
}
