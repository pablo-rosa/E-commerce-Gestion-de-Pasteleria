package com.gestion.gestion_negocio.controladores;

import com.gestion.gestion_negocio.dtos.AñadirDetalleCarritoDto;
import com.gestion.gestion_negocio.dtos.CarritoDto;
import com.gestion.gestion_negocio.servicios.CarritoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones del carrito.
 * Este controlador se enfoca únicamente en la persistencia de datos,
 * siguiendo la arquitectura donde la API maneja solo el acceso a datos.
 */
@RestController
@RequestMapping("/api/carrito")
public class CarritoControlador {

    @Autowired
    private CarritoServicio carritoServicio;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CarritoDto> obtenerCarrito(@PathVariable Long idUsuario) {
        CarritoDto carrito = carritoServicio.obtenerCarritoPorUsuario(idUsuario);
        if (carrito != null) return ResponseEntity.ok(carrito);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/crear/{idUsuario}")
    public ResponseEntity<CarritoDto> crearCarrito(@PathVariable Long idUsuario) {
        CarritoDto carrito = carritoServicio.crearCarrito(idUsuario);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/añadir/{idUsuario}")
    public ResponseEntity<CarritoDto> añadirProducto(
            @PathVariable Long idUsuario,
            @RequestBody AñadirDetalleCarritoDto detalleDto) {
        CarritoDto carrito = carritoServicio.añadirProductoAlCarrito(idUsuario, detalleDto);
        return ResponseEntity.ok(carrito);
    }

    @DeleteMapping("/eliminar/{idUsuario}/{idProducto}")
    public ResponseEntity<String> eliminarProducto(
            @PathVariable Long idUsuario,
            @PathVariable Long idProducto) {
        boolean eliminado = carritoServicio.eliminarProductoDelCarrito(idUsuario, idProducto);
        if (eliminado) return ResponseEntity.ok("Producto eliminado del carrito correctamente.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado en el carrito.");
    }

    @PutMapping("/actualizar/{idUsuario}/{idProducto}")
    public ResponseEntity<CarritoDto> actualizarCantidad(
            @PathVariable Long idUsuario,
            @PathVariable Long idProducto,
            @RequestParam Integer cantidad) {
        CarritoDto carrito = carritoServicio.actualizarCantidadProducto(idUsuario, idProducto, cantidad);
        if (carrito != null) return ResponseEntity.ok(carrito);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/sincronizar/{idUsuario}")
    public ResponseEntity<CarritoDto> sincronizarCarrito(
            @PathVariable Long idUsuario,
            @RequestBody List<AñadirDetalleCarritoDto> detallesSesion) {
        CarritoDto carrito = carritoServicio.sincronizarCarrito(idUsuario, detallesSesion);
        return ResponseEntity.ok(carrito);
    }

    // -------------------- NUEVO ENDPOINT PARA CERRAR CARRITO --------------------
    @PutMapping("/cerrar/{idUsuario}")
    public ResponseEntity<String> cerrarCarrito(@PathVariable Long idUsuario) {
        carritoServicio.cerrarCarrito(idUsuario);
        return ResponseEntity.ok("Carrito cerrado y finalizado correctamente.");
    }
}
