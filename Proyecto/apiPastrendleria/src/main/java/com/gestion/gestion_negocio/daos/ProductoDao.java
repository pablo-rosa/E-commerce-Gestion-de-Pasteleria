package com.gestion.gestion_negocio.daos;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Clase que representa la entidad Producto en la base de datos.
 */
@Entity
@Table(name = "producto", schema = "logica_negocio")
public class ProductoDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", updatable = false)
    private long idProducto;

    @Column(name = "nombre_producto", nullable = false, length = 255)
    private String nombreProducto;

    @Column(name = "descripcion_producto", columnDefinition = "TEXT")
    private String descripcionProducto;

    @Column(name = "precio_producto", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioProducto;  // Cambio a BigDecimal para el precio

    @Column(name = "stock_producto", nullable = false)
    private int stockProducto;

    @Column(name = "categoria_producto", length = 50)
    private String categoriaProducto;

    @Column(name = "imagen_producto", columnDefinition = "BYTEA")
    private byte[] imagenProducto;

    public ProductoDao() { }

    public ProductoDao(String nombreProducto, String descripcionProducto, BigDecimal precioProducto,
                       int stockProducto, String categoriaProducto, byte[] imagenProducto) {
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.stockProducto = stockProducto;
        this.categoriaProducto = categoriaProducto;
        this.imagenProducto = imagenProducto;
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
