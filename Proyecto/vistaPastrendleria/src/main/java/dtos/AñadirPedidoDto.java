package dtos;

import java.util.List;

public class AñadirPedidoDto {
    private Long idUsuario;
    private String direccionPedido;
    private List<AñadirDetallePedidoDto> detalles;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getDireccionPedido() { return direccionPedido; }
    public void setDireccionPedido(String direccionPedido) { this.direccionPedido = direccionPedido; }

    public List<AñadirDetallePedidoDto> getDetalles() { return detalles; }
    public void setDetalles(List<AñadirDetallePedidoDto> detalles) { this.detalles = detalles; }
}
