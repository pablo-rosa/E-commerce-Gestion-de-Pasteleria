package dtos;

import java.math.BigDecimal;

public class DetallePedidoDto {
    private long idDetalle;
    private long idProducto;
    private String nombreProducto; // <-- NUEVO
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // Getters y Setters
    public long getIdDetalle() { return idDetalle; }
    public void setIdDetalle(long idDetalle) { this.idDetalle = idDetalle; }

    public long getIdProducto() { return idProducto; }
    public void setIdProducto(long idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
