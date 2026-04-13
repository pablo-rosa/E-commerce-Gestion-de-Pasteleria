package com.gestion.gestion_negocio.controladores;

import com.gestion.gestion_negocio.dtos.ProductoDto;
import com.gestion.gestion_negocio.servicios.ListaProductoServicio;
import com.gestion.gestion_negocio.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para manejar las peticiones relacionadas con los productos.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {

    private final ListaProductoServicio listaProductoServicio;
    private final ProductoServicio productoServicio;

    @Autowired
    public ProductoControlador(ListaProductoServicio listaProductoServicio, ProductoServicio productoServicio) {
        this.listaProductoServicio = listaProductoServicio;
        this.productoServicio = productoServicio;
    }

    /**
     * Endpoint para obtener la lista de productos.
     * @return Lista de productos (ListaProductoDto)
     */
    @GetMapping("/lista")
    public List<ProductoDto> listarProductos() {
        return listaProductoServicio.obtenerProductos();
    }

    /**
     * Endpoint para obtener el detalle de un producto específico.
     * @param idProducto ID del producto que se quiere consultar
     * @return Detalles del producto (ProductoDto)
     */
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDto> obtenerProducto(@PathVariable long idProducto) {
        try {
            ProductoDto productoDto = productoServicio.obtenerProductoPorId(idProducto);
            return ResponseEntity.ok(productoDto);
        } catch (RuntimeException e) {
            // Si el producto no se encuentra o hay un error al obtenerlo
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);  // O devolver un mensaje de error más detallado
        }
    }

}
