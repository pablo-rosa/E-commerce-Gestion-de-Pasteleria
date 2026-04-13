package com.gestion.gestion_negocio.servicios;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import com.gestion.gestion_negocio.daos.ProductoDao;
import com.gestion.gestion_negocio.dtos.ProductoDto;
import com.gestion.gestion_negocio.repositorios.ProductoRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class ProductoServicio {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServicio(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Obtiene los detalles de un producto por su ID.
     * @param idProducto ID del producto
     * @return DTO con los detalles del producto
     */
    public ProductoDto obtenerProductoPorId(long idProducto) {
        ProductoDao producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Aquí, ya tenemos el byte[] de la imagen directamente desde la base de datos
        byte[] imagenProducto = producto.getImagenProducto();

        return new ProductoDto(
                producto.getIdProducto(),
                producto.getNombreProducto(),
                producto.getDescripcionProducto(),
                producto.getPrecioProducto(),
                producto.getStockProducto(),
                producto.getCategoriaProducto(),
                imagenProducto // Aquí pasamos la imagen como byte[]
        );
    }

    /**
     * Método para obtener la imagen como respuesta binaria
     * @param idProducto ID del producto
     * @return Imagen como byte[] en la respuesta HTTP
     */
    public ResponseEntity<byte[]> obtenerImagenProducto(long idProducto) {
        ProductoDao producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        byte[] imagenProducto = producto.getImagenProducto();

        // Retornamos la imagen como una respuesta binaria con el tipo de contenido adecuado
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)  // O el tipo de imagen que sea (por ejemplo, IMAGE_PNG)
                .body(imagenProducto);
    }
}
