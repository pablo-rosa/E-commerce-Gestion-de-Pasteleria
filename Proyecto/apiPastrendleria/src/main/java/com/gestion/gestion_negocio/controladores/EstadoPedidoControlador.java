package com.gestion.gestion_negocio.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestion.gestion_negocio.dtos.EstadoPedidoDto;
import com.gestion.gestion_negocio.servicios.ActualizarEstadoPedidoServicio;

@RestController
@RequestMapping("/api/pedidos")
public class EstadoPedidoControlador {

    @Autowired
    private ActualizarEstadoPedidoServicio servicio;

    /**
     * Modificar estado del pedido usando form-data (igual que modificarUsuario)
     */
    @PutMapping(value = "/modificarEstado/{idPedido}", consumes = "multipart/form-data")
    public ResponseEntity<String> modificarEstadoPedido(
            @PathVariable Long idPedido,
            @RequestParam("nuevoEstado") String nuevoEstado) {

        System.out.println("API Controlador: PUT recibido -> idPedidoPath=" + idPedido + ", nuevoEstado=" + nuevoEstado);

        EstadoPedidoDto dto = new EstadoPedidoDto();
        dto.setIdPedido(idPedido);
        dto.setNuevoEstado(nuevoEstado);

        boolean actualizado = servicio.actualizarEstadoPedido(dto);
        if (actualizado) {
            System.out.println("API Controlador: Pedido actualizado correctamente");
            return ResponseEntity.ok("Estado del pedido actualizado correctamente");
        } else {
            System.out.println("API Controlador: Pedido no encontrado");
            return ResponseEntity.status(404).body("Pedido no encontrado");
        }
    }
}
