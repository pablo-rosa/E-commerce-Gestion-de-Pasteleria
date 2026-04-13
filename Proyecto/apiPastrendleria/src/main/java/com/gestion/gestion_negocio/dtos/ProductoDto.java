package com.gestion.gestion_negocio.dtos;

import java.math.BigDecimal;

/**
 * DTO para representar un producto con toda su información detallada, incluyendo la imagen como byte[].
 */
public class ProductoDto {

    private long idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private BigDecimal precioProducto;
    private int stockProducto;
    private String categoriaProducto;
    private byte[] imagenProducto;  // Cambiado a byte[] para la imagen

    // Constructor
    public ProductoDto(long idProducto, String nombreProducto, String descripcionProducto,
                       BigDecimal precioProducto, int stockProducto, String categoriaProducto, byte[] imagenProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.stockProducto = stockProducto;
        this.categoriaProducto = categoriaProducto;
        this.imagenProducto = imagenProducto;  // Asignar imagen como byte[]
    }

    // Getters y Setters
    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public byte[] getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(byte[] imagenProducto) {
        this.imagenProducto = imagenProducto;
    }
}
