package com.gestion.gestion_negocio.servicios;


import com.gestion.gestion_negocio.dtos.PedidoDto;
import com.gestion.gestion_negocio.daos.PedidoDao;
import com.gestion.gestion_negocio.dtos.DetallePedidoDto;
import com.gestion.gestion_negocio.repositorios.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListaPedidoServicio {

    private final PedidoRepository pedidoRepository;

    public ListaPedidoServicio(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoDto> listarPedidos() {

        return pedidoRepository.findAll().stream()
                .map(pedido ->
                    new PedidoDto(
                        pedido.getIdPedido(),
                        pedido.getIdUsuario(),
                        pedido.getFechaPedido(),
                        pedido.getDireccionPedido(),
                        pedido.getEstadoPedido(),
                        pedido.getTotalPedido(),
                        pedido.getDetalles().stream()
                            .map(det -> new DetallePedidoDto(
                                    det.getIdDetalle(),
                                    det.getIdProducto(),
                                    det.getNombreProducto(),   // <-- nombre del producto
                                    det.getCantidad(),
                                    det.getPrecioUnitario(),
                                    det.getSubtotal()
                            ))
                            .collect(Collectors.toList())
                    )
                ).collect(Collectors.toList());
    }
 
}