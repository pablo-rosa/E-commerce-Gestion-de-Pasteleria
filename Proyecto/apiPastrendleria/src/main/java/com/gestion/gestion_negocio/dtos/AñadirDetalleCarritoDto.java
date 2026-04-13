package com.gestion.gestion_negocio.dtos;

import java.math.BigDecimal;

/**
 * DTO para añadir un detalle al carrito.
 * Se utiliza para recibir información de un producto que se añade al carrito.
 */
public class AñadirDetalleCarritoDto {
    
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;

    /**
     * Constructor por defecto.
     */
    public AñadirDetalleCarritoDto() {}

    // ========== GETTERS ==========

    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    // ========== SETTERS ==========

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
