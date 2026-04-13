package com.gestion.gestion_negocio.dtos;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para transferir datos de un detalle de carrito.
 * Se utiliza para enviar información de los detalles del carrito desde la API al cliente.
 */
public class DetalleCarritoDto {

    private Long idDetalleCarrito;
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    /**
     * Constructor por defecto.
     */
    public DetalleCarritoDto() {}

    /**
     * Constructor completo.
     */
    public DetalleCarritoDto(Long idDetalleCarrito, Long idProducto, String nombreProducto,
                            Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.idDetalleCarrito = idDetalleCarrito;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // ========== GETTERS ==========

    public Long getIdDetalleCarrito() {
        return idDetalleCarrito;
    }

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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    // ========== SETTERS ==========

    public void setIdDetalleCarrito(Long idDetalleCarrito) {
        this.idDetalleCarrito = idDetalleCarrito;
    }

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

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
