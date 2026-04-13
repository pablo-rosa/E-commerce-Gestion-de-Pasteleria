package utilidades;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class FormateoFechaUtilidad {

	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    // Método estático para formatear fecha ISO 8601
    public static String formatearFecha(String fechaIso) {
        try {
            OffsetDateTime fecha = OffsetDateTime.parse(fechaIso);
            return fecha.format(FORMATO);
        } catch (Exception e) {
            System.out.println("Error al formatear fecha: " + fechaIso);
            return fechaIso; // Retorna la original si falla
        }
    }
}
