package servicios;

import dtos.ListaUsuarioDto;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class EliminarServicio {

    private final ListaUsuarioServicio listaServicio = new ListaUsuarioServicio();

    // Ahora recibe confirmacion como parámetro
    public String eliminarUsuario(long idUsuario, String confirmacion) {
        List<ListaUsuarioDto> usuarios = listaServicio.obtenerUsuarios();

        if (usuarios == null || usuarios.isEmpty()) {
            return "No hay usuarios registrados.";
        }

        // Buscar usuario a eliminar
        ListaUsuarioDto usuarioAEliminar = null;
        for (ListaUsuarioDto usuario : usuarios) {
            if (usuario.getIdUsuario() == idUsuario) {
                usuarioAEliminar = usuario;
                break;
            }
        }

        if (usuarioAEliminar == null) {
            return "Usuario no encontrado.";
        }

        // Validar confirmación
        String nombre = usuarioAEliminar.getNombreUsuario();
        String fraseEsperada = "Si(" + nombre + ")SeguroDeEliminar";
        if (!fraseEsperada.equals(confirmacion)) {
            return "La confirmación no coincide. Debes escribir exactamente: " + fraseEsperada;
        }

        // Contar administradores
        int adminCount = 0;
        for (ListaUsuarioDto usuario : usuarios) {
            if ("admin".equalsIgnoreCase(usuario.getRol())) {
                adminCount++;
            }
        }

        if ("admin".equalsIgnoreCase(usuarioAEliminar.getRol()) && adminCount <= 1) {
            return "No se puede eliminar el último administrador.";
        }

        // Llamada a la API
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8081/api/eliminar/usuario/" + idUsuario);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                return "Usuario eliminado exitosamente.";
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                return "Usuario no encontrado.";
            } else {
                return "Error al eliminar el usuario. Código de respuesta: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al conectar con la API: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
