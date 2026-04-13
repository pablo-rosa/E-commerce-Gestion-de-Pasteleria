package com.gestion.gestion_negocio.servicios;

import com.gestion.gestion_negocio.daos.ProductoDao;
import com.gestion.gestion_negocio.dtos.ProductoDto;
import com.gestion.gestion_negocio.repositorios.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que maneja la lógica de negocio para obtener la lista de productos.
 */
@Service
public class ListaProductoServicio {

    private final ProductoRepository productoRepository;

    @Autowired
    public ListaProductoServicio(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Obtiene la lista de productos y la convierte en DTOs.
     * @return Lista de productos como DTOs (ProductoDto)
     */
    public List<ProductoDto> obtenerProductos() {
        List<ProductoDao> productos = productoRepository.findAll();

        // Convertir los productos a DTOs
        return productos.stream()
                .map(producto -> new ProductoDto(
                        producto.getIdProducto(),
                        producto.getNombreProducto(),
                        producto.getDescripcionProducto(),
                        producto.getPrecioProducto(),
                        producto.getStockProducto(),
                        producto.getCategoriaProducto(),
                        producto.getImagenProducto() // Aquí usamos la imagen como byte[]
                ))
                .collect(Collectors.toList());
    }
}
