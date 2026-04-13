package controladores;

import java.io.IOException;

import dtos.RecuperacionContrasenaDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.RecuperacionContrasenaServicio;
import utilidades.TokenUtilidad;
import utilidades.ContrasenaSeguraUtilidad;
import utilidades.EscritorDeLogsUtilidad;

@WebServlet("/procesarCambioContrasena")
public class RestablecerContrasenaControlador extends HttpServlet {

    private RecuperacionContrasenaServicio servicio;

    @Override
    public void init() throws ServletException {
        servicio = new RecuperacionContrasenaServicio();
        EscritorDeLogsUtilidad.registrar("RestablecerContrasenaControlador - init() - Servicio de recuperación inicializado");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("RestablecerContrasenaControlador - doPost() - Inicio de petición");

        String token = request.getParameter("token");
        String nuevaPassword = request.getParameter("nuevaPassword");
        String confirmar = request.getParameter("confirmarPassword");

        EscritorDeLogsUtilidad.registrar("RestablecerContrasenaControlador - Parámetros recibidos: token=" + token);

        // Validar que las contraseñas coincidan
        if (!nuevaPassword.equals(confirmar)) {
            String error = "Las contraseñas no coinciden.";
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RestablecerContrasenaControlador - ERROR: " + error + " para token=" + token);
            request.getRequestDispatcher("restablecerContrasena.jsp?token=" + token).forward(request, response);
            return;
        }

        // Verificar política de seguridad de la contraseña
        if (!ContrasenaSeguraUtilidad.esSegura(nuevaPassword)) {
            String error = "La contraseña no cumple la política de seguridad.";
            request.setAttribute("errorMessage", "La contraseña debe tener al menos 8 caracteres, incluir una mayúscula, una minúscula, un número y un símbolo especial.");
            EscritorDeLogsUtilidad.registrar("RestablecerContrasenaControlador - ERROR: " + error + " para token=" + token);
            request.getRequestDispatcher("restablecerContrasena.jsp?token=" + token).forward(request, response);
            return;
        }

        // Validar token
        RecuperacionContrasenaDto dto = TokenUtilidad.validarTokenRecuperacion(token);
        if (dto == null) {
            String error = "Token inválido o expirado.";
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RestablecerContrasenaControlador - ERROR: " + error + " token=" + token);
            request.getRequestDispatcher("restablecerContrasena.jsp").forward(request, response);
            return;
        }

        // Validación para que la nueva contraseña no sea igual a la actual
        boolean esIgual = servicio.esContrasenaIgualActual(dto.getEmailUsuario(), nuevaPassword);
        if (esIgual) {
            String error = "La nueva contraseña es igual a la actual.";
            request.setAttribute("errorMessage", "La nueva contraseña no puede ser igual a la contraseña actual.");
            EscritorDeLogsUtilidad.registrar("RestablecerContrasenaControlador - ERROR: " + error + " usuario=" + dto.getEmailUsuario());
            request.getRequestDispatcher("restablecerContrasena.jsp?token=" + token).forward(request, response);
            return;
        }

        // Actualizar la contraseña
        dto.setNuevaPassword(nuevaPassword);
        boolean actualizado = servicio.actualizarContrasena(dto);

        if (actualizado) {
            // Eliminar token tras actualización
            TokenUtilidad.eliminarTokenRecuperacion(token);
            EscritorDeLogsUtilidad.registrar("RestablecerContrasenaControlador - Contraseña actualizada correctamente para usuario=" + dto.getEmailUsuario());

            request.setAttribute("successMessage", "¡Contraseña restablecida exitosamente!");
            request.getRequestDispatcher("restablecerContrasena.jsp").forward(request, response);
        } else {
            String error = "No se pudo actualizar la contraseña.";
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RestablecerContrasenaControlador - ERROR: " + error + " usuario=" + dto.getEmailUsuario());
            request.getRequestDispatcher("restablecerContrasena.jsp?token=" + token).forward(request, response);
        }
    }
}
