package com.gestion.gestion_negocio.controladores;

import com.gestion.gestion_negocio.dtos.AñadirPedidoDto;
import com.gestion.gestion_negocio.servicios.AñadirPedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class AñadirPedidoControlador {

    @Autowired
    private AñadirPedidoServicio pedidoServicio;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarPedido(@RequestBody AñadirPedidoDto pedidoDto) {
        try {
            if (pedidoDto.getDetalles() == null || pedidoDto.getDetalles().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El pedido debe contener al menos un producto.");
            }

            pedidoServicio.registrarPedido(pedidoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido registrado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el pedido.");
        }
    }
}
