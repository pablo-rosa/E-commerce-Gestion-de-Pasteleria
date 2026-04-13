package com.gestion.gestion_negocio.servicios;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.gestion_negocio.daos.PedidoDao;
import com.gestion.gestion_negocio.daos.DetallePedidoDao;
import com.gestion.gestion_negocio.dtos.AñadirPedidoDto;
import com.gestion.gestion_negocio.dtos.AñadirDetallePedidoDto;
import com.gestion.gestion_negocio.repositorios.PedidoRepository;

@Service
public class AñadirPedidoServicio {

    @Autowired
    private PedidoRepository pedidoRepository;

    public void registrarPedido(AñadirPedidoDto pedidoDto) {
        PedidoDao pedido = new PedidoDao();
        pedido.setIdUsuario(pedidoDto.getIdUsuario());
        pedido.setDireccionPedido(pedidoDto.getDireccionPedido());
        pedido.setEstadoPedido("PENDIENTE");
        pedido.setFechaPedido(OffsetDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        List<DetallePedidoDao> detalles = new ArrayList<>();

        for (AñadirDetallePedidoDto detalleDto : pedidoDto.getDetalles()) {
            DetallePedidoDao detalle = new DetallePedidoDao();
            detalle.setPedido(pedido);
            detalle.setIdProducto(detalleDto.getIdProducto());
            detalle.setNombreProducto(detalleDto.getNombreProducto());
            detalle.setCantidad(detalleDto.getCantidad());
            detalle.setPrecioUnitario(detalleDto.getPrecioUnitario());
            detalle.setSubtotal(detalleDto.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleDto.getCantidad())));
            
            total = total.add(detalle.getSubtotal());
            detalles.add(detalle);
        }

        pedido.setTotalPedido(total);
        pedido.setDetalles(detalles);

        pedidoRepository.save(pedido);
    }
}
