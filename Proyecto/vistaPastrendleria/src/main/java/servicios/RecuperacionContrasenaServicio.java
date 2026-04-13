package servicios;

import dtos.RecuperacionContrasenaDto;
import dtos.LoginUsuarioDto;
import utilidades.EmailUtilidad;
import utilidades.TokenUtilidad;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecuperacionContrasenaServicio {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public boolean emailExisteEnApi(String email) {
        try {
            URL url = new URL("http://localhost:8081/api/usuarios/existe?email=" + email);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            return conexion.getResponseCode() == HttpURLConnection.HTTP_OK &&
                    Boolean.parseBoolean(new BufferedReader(
                            new InputStreamReader(conexion.getInputStream())).readLine());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean enviarEnlaceRecuperacion(String email, String token) {
        try {
            EmailUtilidad emailUtil = new EmailUtilidad();
            String enlace = "http://pablodominio.sbs/vistaPastrendleria/restablecerContrasena.jsp?token=" + token;
            String asunto = "Restablecimiento de contraseña – El Diario Fuun";
            String cuerpo = "Haz clic en el siguiente enlace para restablecer tu contraseña:\n\n" +
                    enlace + "\n\nSi no solicitaste esta acción, ignora este correo.";
            emailUtil.enviarCorreo(email, asunto, cuerpo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarContrasena(RecuperacionContrasenaDto dto) {
        try {
            URL url = new URL("http://localhost:8081/api/usuarios/recuperar-password");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conexion.setDoOutput(true);

            String parametros = "email=" + dto.getEmailUsuario() +
                    "&nuevaPassword=" + dto.getNuevaPassword();

            try (OutputStream os = conexion.getOutputStream()) {
                os.write(parametros.getBytes());
            }

            return conexion.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Valida si la nueva contraseña es igual a la actual consultando el endpoint de login.
     *
     * @param email el email del usuario.
     * @param nuevaPassword la contraseña nueva que se quiere establecer.
     * @return true si la contraseña nueva ES igual a la actual (no se debe permitir),
     *         false si es diferente (válida para cambio).
     */
    public boolean esContrasenaIgualActual(String email, String nuevaPassword) {
        try {
            URL url = new URL("http://localhost:8081/api/login/validarUsuario");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setDoOutput(true);

            LoginUsuarioDto loginDto = new LoginUsuarioDto();
            loginDto.setEmail(email);
            loginDto.setPassword(nuevaPassword);

            String jsonInput = objectMapper.writeValueAsString(loginDto);

            try (OutputStream os = conexion.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            int responseCode = conexion.getResponseCode();

            // 200 -> contraseña actual (igual)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;  // NO permitir porque es igual
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                return false; // permitido, diferente contraseña
            } else {
                // Por seguridad asumimos que es igual si no podemos validar correctamente
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Por seguridad bloqueamos el cambio si no se puede validar
            return true;
        }
    }
}
