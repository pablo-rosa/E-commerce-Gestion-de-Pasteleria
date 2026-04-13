package com.gestion.gestion_negocio.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO (Data Transfer Object) para transferir datos de un carrito.
 * Se utiliza para enviar información del carrito desde la API al cliente.
 */
public class CarritoDto {

    private Long idCarrito;
    private Long idUsuario;
    private OffsetDateTime fechaCarrito;
    private String direccionCarrito;
    private String estadoCarrito;
    private BigDecimal totalCarrito;
    private List<DetalleCarritoDto> detalles;

    /**
     * Constructor por defecto.
     */
    public CarritoDto() {}

    /**
     * Constructor completo.
     */
    public CarritoDto(Long idCarrito, Long idUsuario, OffsetDateTime fechaCarrito,
                     String direccionCarrito, String estadoCarrito,
                     BigDecimal totalCarrito, List<DetalleCarritoDto> detalles) {
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
        this.fechaCarrito = fechaCarrito;
        this.direccionCarrito = direccionCarrito;
        this.estadoCarrito = estadoCarrito;
        this.totalCarrito = totalCarrito;
        this.detalles = detalles;
    }

    // ========== GETTERS ==========

    public Long getIdCarrito() {
        return idCarrito;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public OffsetDateTime getFechaCarrito() {
        return fechaCarrito;
    }

    public String getDireccionCarrito() {
        return direccionCarrito;
    }

    public String getEstadoCarrito() {
        return estadoCarrito;
    }

    public BigDecimal getTotalCarrito() {
        return totalCarrito;
    }

    public List<DetalleCarritoDto> getDetalles() {
        return detalles;
    }

    // ========== SETTERS ==========

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setFechaCarrito(OffsetDateTime fechaCarrito) {
        this.fechaCarrito = fechaCarrito;
    }

    public void setDireccionCarrito(String direccionCarrito) {
        this.direccionCarrito = direccionCarrito;
    }

    public void setEstadoCarrito(String estadoCarrito) {
        this.estadoCarrito = estadoCarrito;
    }

    public void setTotalCarrito(BigDecimal totalCarrito) {
        this.totalCarrito = totalCarrito;
    }

    public void setDetalles(List<DetalleCarritoDto> detalles) {
        this.detalles = detalles;
    }
}
