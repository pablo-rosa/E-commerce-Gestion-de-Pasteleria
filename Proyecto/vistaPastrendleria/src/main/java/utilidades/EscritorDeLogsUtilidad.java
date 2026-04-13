package utilidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EscritorDeLogsUtilidad {

    // Carpeta de logs dentro del proyecto desplegado
	private static final String RUTA_LOGS = "C:\\vistaPastrendleria_logs\\";

    // Obtiene la ruta completa del archivo correspondiente al día actual
    private static String obtenerArchivoDelDia() {
        // Nombre del archivo = fecha actual dd-MM-yyyy.txt
        String nombreArchivo = new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".txt";

        // Crear carpeta logs si no existe
        File carpeta = new File(RUTA_LOGS);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        return RUTA_LOGS + nombreArchivo;
    }

    // Registra un mensaje en el log del día actual
    public static void registrar(String mensaje) {
        String archivoLog = obtenerArchivoDelDia();
        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String linea = hora + "- " + mensaje;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(archivoLog), true))) {
            bw.write(linea);
            bw.newLine(); // Siempre en la siguiente línea
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
