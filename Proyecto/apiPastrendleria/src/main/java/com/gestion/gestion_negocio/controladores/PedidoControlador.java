package com.gestion.gestion_negocio.controladores;

import com.gestion.gestion_negocio.dtos.PedidoDto;
import com.gestion.gestion_negocio.servicios.ListaPedidoServicio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoControlador {

    private final ListaPedidoServicio listaPedidoServicio;

    public PedidoControlador(ListaPedidoServicio listaPedidoServicio) {
        this.listaPedidoServicio = listaPedidoServicio;
    }

    @GetMapping("/lista")
    public List<PedidoDto> listarPedidos() {
        return listaPedidoServicio.listarPedidos();
    }
    
}
