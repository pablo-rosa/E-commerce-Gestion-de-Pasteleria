package servicios;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ActualizarEstadoPedidoServicio {

    public String actualizarEstadoPedido(Long idPedido, String nuevoEstado) {
        String boundary = "*****" + System.currentTimeMillis() + "*****";

        try {
            URL url = new URL("http://localhost:8081/api/pedidos/modificarEstado/" + idPedido);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setDoOutput(true);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes("--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"nuevoEstado\"\r\n\r\n");
            dos.writeBytes(nuevoEstado + "\r\n");
            dos.writeBytes("--" + boundary + "--\r\n");

            dos.flush();
            dos.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                StringBuilder response = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                }
                return response.toString();
            } else {
                return "Error: Código HTTP " + responseCode;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error al conectar con la API: " + e.getMessage();
        }
    }
}
