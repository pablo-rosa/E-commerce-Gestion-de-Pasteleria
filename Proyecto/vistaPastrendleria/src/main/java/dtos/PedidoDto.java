package dtos;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDto {
    private long idPedido;
    private long idUsuario;
    private String fechaPedido;
    private String direccionPedido;
    private String estadoPedido;
    private BigDecimal totalPedido;
    private List<DetallePedidoDto> detalles;

    // Getters y Setters
    public long getIdPedido() { return idPedido; }
    public void setIdPedido(long idPedido) { this.idPedido = idPedido; }

    public long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(long idUsuario) { this.idUsuario = idUsuario; }

    public String getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(String fechaPedido) { this.fechaPedido = fechaPedido; }

    public String getDireccionPedido() { return direccionPedido; }
    public void setDireccionPedido(String direccionPedido) { this.direccionPedido = direccionPedido; }

    public String getEstadoPedido() { return estadoPedido; }
    public void setEstadoPedido(String estadoPedido) { this.estadoPedido = estadoPedido; }

    public BigDecimal getTotalPedido() { return totalPedido; }
    public void setTotalPedido(BigDecimal totalPedido) { this.totalPedido = totalPedido; }

    public List<DetallePedidoDto> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoDto> detalles) { this.detalles = detalles; }
}
