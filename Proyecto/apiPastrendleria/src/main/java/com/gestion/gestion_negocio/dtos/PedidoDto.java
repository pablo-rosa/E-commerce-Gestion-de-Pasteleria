package com.gestion.gestion_negocio.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PedidoDto {

    private Long idPedido;
    private Long idUsuario;
    private OffsetDateTime fechaPedido;
    private String direccionPedido;
    private String estadoPedido;
    private BigDecimal totalPedido;
    private List<DetallePedidoDto> detalles;

    public PedidoDto() {}

    public PedidoDto(Long idPedido, Long idUsuario, OffsetDateTime fechaPedido,
                     String direccionPedido, String estadoPedido,
                     BigDecimal totalPedido, List<DetallePedidoDto> detalles) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.fechaPedido = fechaPedido;
        this.direccionPedido = direccionPedido;
        this.estadoPedido = estadoPedido;
        this.totalPedido = totalPedido;
        this.detalles = detalles;
    }

    // ==== GETTERS ====

    public Long getIdPedido() {
        return idPedido;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public OffsetDateTime getFechaPedido() {
        return fechaPedido;
    }

    public String getDireccionPedido() {
        return direccionPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public List<DetallePedidoDto> getDetalles() {
        return detalles;
    }

    // ==== SETTERS ====

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setFechaPedido(OffsetDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setDireccionPedido(String direccionPedido) {
        this.direccionPedido = direccionPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    public void setDetalles(List<DetallePedidoDto> detalles) {
        this.detalles = detalles;
    }
}
