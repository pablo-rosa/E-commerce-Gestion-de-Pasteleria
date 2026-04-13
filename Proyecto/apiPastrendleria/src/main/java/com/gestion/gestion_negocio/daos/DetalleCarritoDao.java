package com.gestion.gestion_negocio.daos;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * DAO (Data Access Object) para la entidad DetalleCarrito.
 * Representa un detalle individual de un producto dentro de un carrito.
 */
@Entity
@Table(name = "detalles_carrito", schema = "logica_negocio")
public class DetalleCarritoDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_carrito")
    private Long idDetalleCarrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito", nullable = false)
    private CarritoDao carrito;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "cantidad_producto", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public DetalleCarritoDao() {}

    // ========== GETTERS ==========

    public Long getIdDetalleCarrito() {
        return idDetalleCarrito;
    }

    public CarritoDao getCarrito() {
        return carrito;
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

    public void setCarrito(CarritoDao carrito) {
        this.carrito = carrito;
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
