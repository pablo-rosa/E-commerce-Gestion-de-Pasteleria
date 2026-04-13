package servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import dtos.ListaUsuarioDto;

@Service
public class ListaUsuarioServicio {

    private final String API_URL = "http://localhost:8081/api/usuarios/lista";

    // Método para obtener usuarios desde la API usando RestTemplate
    public List<ListaUsuarioDto> obtenerUsuariosDesdeApi() {
        RestTemplate restTemplate = new RestTemplate();
        ListaUsuarioDto[] usuarios = restTemplate.getForObject(API_URL, ListaUsuarioDto[].class);
        return Arrays.asList(usuarios);
    }

    // Método para obtener usuarios desde otro servicio usando HttpClient
    public List<ListaUsuarioDto> obtenerUsuariosDesdeServicio() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Error en la API. Código: " + response.statusCode());
        }

        Gson gson = new Gson();
        ListaUsuarioDto[] usuariosArray;

        try {
            usuariosArray = gson.fromJson(response.body(), ListaUsuarioDto[].class);
        } catch (JsonSyntaxException e) {
            throw new Exception("El JSON no tiene el formato esperado");
        }

        return Arrays.asList(usuariosArray);
    }

    // Método general para obtener los usuarios (primero de la API, luego del otro servicio si es necesario)
    public List<ListaUsuarioDto> obtenerUsuarios() {
        // Preferir la API, pero si no hay datos, usar el otro servicio
        List<ListaUsuarioDto> usuarios = obtenerUsuariosDesdeApi();
        if (usuarios.isEmpty()) {
            try {
                // Si la API no devuelve resultados, obtenemos de otro servicio
                usuarios = obtenerUsuariosDesdeServicio();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usuarios;
    }
}
