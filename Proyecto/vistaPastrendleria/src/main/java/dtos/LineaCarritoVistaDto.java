package dtos;

import java.math.BigDecimal;

/**
 * DTO de apoyo para la vista del carrito.
 * Contiene todos los datos necesarios para pintar una línea del carrito
 * sin que la JSP tenga que hacer cálculos ni lógica adicional.
 */
public class LineaCarritoVistaDto {

    private long idProducto;
    private String nombreProducto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal totalLinea;

    public LineaCarritoVistaDto(long idProducto,
                                String nombreProducto,
                                int cantidad,
                                BigDecimal precioUnitario,
                                BigDecimal totalLinea) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.totalLinea = totalLinea;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getTotalLinea() {
        return totalLinea;
    }
}

