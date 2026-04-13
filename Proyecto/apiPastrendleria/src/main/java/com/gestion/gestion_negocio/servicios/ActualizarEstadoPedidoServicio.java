package com.gestion.gestion_negocio.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.gestion_negocio.daos.PedidoDao;
import com.gestion.gestion_negocio.dtos.EstadoPedidoDto;
import com.gestion.gestion_negocio.repositorios.PedidoRepository;

@Service
public class ActualizarEstadoPedidoServicio {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public boolean actualizarEstadoPedido(EstadoPedidoDto dto) {
        System.out.println("API Servicio: Recibido DTO -> idPedido=" + dto.getIdPedido() + ", nuevoEstado=" + dto.getNuevoEstado());

        Optional<PedidoDao> pedidoOpt = pedidoRepository.findById(dto.getIdPedido());
        if (pedidoOpt.isPresent()) {
            PedidoDao pedido = pedidoOpt.get();
            System.out.println("API Servicio: Pedido encontrado, estado actual=" + pedido.getEstadoPedido());

            pedido.setEstadoPedido(dto.getNuevoEstado());
            pedidoRepository.save(pedido);

            System.out.println("API Servicio: Estado actualizado a " + dto.getNuevoEstado());
            return true;
        } else {
            System.out.println("API Servicio: Pedido con id " + dto.getIdPedido() + " no encontrado");
            return false;
        }
    }
}
