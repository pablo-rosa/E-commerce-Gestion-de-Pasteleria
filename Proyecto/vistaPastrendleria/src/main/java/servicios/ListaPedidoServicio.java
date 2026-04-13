package servicios;

import dtos.PedidoDto;
import utilidades.FormateoFechaUtilidad;

import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.time.OffsetDateTime;

import org.springframework.web.client.RestTemplate;

public class ListaPedidoServicio {

    private final String API_URL_PEDIDOS = "http://localhost:8081/api/pedidos/lista";

    public List<PedidoDto> obtenerPedidos() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            PedidoDto[] pedidosArray = restTemplate.getForObject(API_URL_PEDIDOS, PedidoDto[].class);

            if (pedidosArray != null) {

                // Ordenar de más reciente a más antiguo de forma sencilla
                Arrays.sort(pedidosArray, (p1, p2) -> {
                    String f1Str = p1.getFechaPedido();
                    String f2Str = p2.getFechaPedido();

                    OffsetDateTime f1 = null;
                    OffsetDateTime f2 = null;

                    try { f1 = OffsetDateTime.parse(f1Str); } catch (Exception e) { f1 = OffsetDateTime.MIN; }
                    try { f2 = OffsetDateTime.parse(f2Str); } catch (Exception e) { f2 = OffsetDateTime.MIN; }

                    // Reversed: más reciente primero
                    return f2.compareTo(f1);
                });

                // Formatear fecha para la vista
                for (PedidoDto pedido : pedidosArray) {
                    pedido.setFechaPedido(FormateoFechaUtilidad.formatearFecha(pedido.getFechaPedido()));
                }

                return Arrays.asList(pedidosArray);
            } else {
                return List.of();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
