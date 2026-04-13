package servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import dtos.ListaProductoDto;

import java.util.Arrays;
import java.util.List;

@Service
public class ListaProductoServicio {

    private final String API_URL_LISTA = "http://localhost:8081/api/productos/lista";
    private final String API_URL_PRODUCTO = "http://localhost:8081/api/productos/";

    // Obtener todos los productos
    public List<ListaProductoDto> obtenerProductos() {
        RestTemplate restTemplate = new RestTemplate();
        ListaProductoDto[] productos = restTemplate.getForObject(API_URL_LISTA, ListaProductoDto[].class);
        return Arrays.asList(productos);
    }

    // Obtener un producto por su ID
    public ListaProductoDto obtenerProductoPorId(long id) {
        RestTemplate restTemplate = new RestTemplate();
        ListaProductoDto producto = restTemplate.getForObject(API_URL_PRODUCTO + id, ListaProductoDto.class);
        return producto;
    }
}
