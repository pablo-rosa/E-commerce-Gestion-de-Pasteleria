package servicios;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import dtos.ModificarUsuarioDto;
import dtos.ListaUsuarioDto;

public class ModificarServicio {

    private final ListaUsuarioServicio listaServicio = new ListaUsuarioServicio();

    public String modificarUsuario(long idUsuario, String nuevoNombre, String nuevoTelefono, String nuevoRol, byte[] nuevaFoto) {
        // Primero, obtener los usuarios para verificar si es el último administrador
        List<ListaUsuarioDto> usuarios = listaServicio.obtenerUsuarios();
        
        if (usuarios == null || usuarios.isEmpty()) {
            return "No hay usuarios registrados.";
        }

        // Buscar usuario a modificar
        ListaUsuarioDto usuarioAModificar = null;
        for (ListaUsuarioDto usuario : usuarios) {
            if (usuario.getIdUsuario() == idUsuario) {
                usuarioAModificar = usuario;
                break;
            }
        }

        if (usuarioAModificar == null) {
            return "Usuario no encontrado.";
        }

        // Contar administradores
        int adminCount = 0;
        for (ListaUsuarioDto usuario : usuarios) {
            if ("admin".equalsIgnoreCase(usuario.getRol())) {
                adminCount++;
            }
        }

        // Si el usuario a modificar es administrador y el número de administradores es 1
        if ("admin".equalsIgnoreCase(usuarioAModificar.getRol()) && adminCount <= 1 && !nuevoRol.equalsIgnoreCase("admin")) {
            return "No se puede cambiar el rol de administrador al último administrador.";
        }

        // Continuar con la modificación del usuario
        String boundary = "*****" + System.currentTimeMillis() + "*****"; // Límite para multipart
        try {
            URL url = new URL("http://localhost:8081/api/modificar/modificarUsuario/" + idUsuario);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setDoOutput(true);

            // Construir cuerpo multipart
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            // Campo nuevoNombre
            if (nuevoNombre != null) {
                dos.writeBytes("--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"nuevoNombre\"\r\n\r\n");
                dos.writeBytes(nuevoNombre + "\r\n");
            }

            // Campo nuevoTelefono
            if (nuevoTelefono != null) {
                dos.writeBytes("--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"nuevoTelefono\"\r\n\r\n");
                dos.writeBytes(nuevoTelefono + "\r\n");
            }

            // Campo nuevoRol
            if (nuevoRol != null) {
                dos.writeBytes("--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"nuevoRol\"\r\n\r\n");
                dos.writeBytes(nuevoRol + "\r\n");
            }

            // Campo nuevaFoto
            if (nuevaFoto != null) {
                dos.writeBytes("--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"nuevaFoto\"; filename=\"foto.jpg\"\r\n");
                dos.writeBytes("Content-Type: image/jpeg\r\n\r\n");
                dos.write(nuevaFoto);
                dos.writeBytes("\r\n");
            }

            dos.writeBytes("--" + boundary + "--\r\n");
            dos.flush();
            dos.close();

            // Leer respuesta
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                StringBuilder response = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                }
                return "Usuario modificado exitosamente.";
            } else {
                return "Error: Código de respuesta HTTP " + responseCode;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al conectar con la API: " + e.getMessage();
        }
    }

    public ModificarUsuarioDto obtenerUsuarioPorId(long idUsuario) {
        try {
            URL url = new URL("http://localhost:8081/api/modificar/buscarUsuario/" + idUsuario);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Leer la respuesta y convertirla en un objeto UsuarioDTO
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                // Convertir la respuesta JSON en un objeto ModificarUsuarioDto
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.toString(), ModificarUsuarioDto.class);
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
