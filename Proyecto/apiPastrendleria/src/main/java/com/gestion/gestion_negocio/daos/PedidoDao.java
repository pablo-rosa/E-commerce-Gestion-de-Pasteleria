package com.gestion.gestion_negocio.daos;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "pedido", schema = "logica_negocio")
public class PedidoDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "fecha_pedido", nullable = false)
    private OffsetDateTime fechaPedido;

    @Column(name = "direccion_pedido", nullable = false, length = 255)
    private String direccionPedido;

    @Column(name = "estado_pedido", nullable = false, length = 50)
    private String estadoPedido;

    @Column(name = "total_pedido", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetallePedidoDao> detalles;

    public PedidoDao() {}

    // GETTERS & SETTERS — Todos los que tu servicio necesita:

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public OffsetDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(OffsetDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getDireccionPedido() {
        return direccionPedido;
    }

    public void setDireccionPedido(String direccionPedido) {
        this.direccionPedido = direccionPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    public List<DetallePedidoDao> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoDao> detalles) {
        this.detalles = detalles;
    }
}
