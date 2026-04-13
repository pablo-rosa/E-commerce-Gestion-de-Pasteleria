package com.gestion.gestion_negocio.daos;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Carrito.
 * Representa un carrito de compras persistente en la base de datos.
 * El carrito es similar a un pedido pero en estado activo antes de ser finalizado.
 */
@Entity
@Table(name = "carrito", schema = "logica_negocio")
public class CarritoDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long idCarrito;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "fecha_carrito", nullable = false)
    private OffsetDateTime fechaCarrito;

    @Column(name = "direccion_carrito", length = 255)
    private String direccionCarrito;

    @Column(name = "estado_carrito", nullable = false, length = 50)
    private String estadoCarrito;

    @Column(name = "total_carrito", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCarrito;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleCarritoDao> detalles;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public CarritoDao() {}

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

    public List<DetalleCarritoDao> getDetalles() {
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

    public void setDetalles(List<DetalleCarritoDao> detalles) {
        this.detalles = detalles;
    }
}
