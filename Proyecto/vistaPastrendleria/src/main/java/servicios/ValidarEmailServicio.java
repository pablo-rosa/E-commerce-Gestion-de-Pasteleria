package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ValidarEmailServicio {

    /**
     * Consulta la API para verificar si un email está registrado.
     * 
     * @param email El email a verificar.
     * @return true si el email existe, false si no.
     */
    public boolean emailExiste(String email) {
        try {
            URL url = new URL("http://localhost:8081/api/usuarios/existe?email=" + email);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            int responseCode = conexion.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String response = in.readLine();  // "true" o "false"
                in.close();
                return Boolean.parseBoolean(response);
            } else {
                System.out.println("Error al consultar email. Código HTTP: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // En caso de error, asumimos que no existe
    }
}
