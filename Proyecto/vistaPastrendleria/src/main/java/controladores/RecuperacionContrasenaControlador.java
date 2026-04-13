package controladores;

import java.io.IOException;
import dtos.RecuperacionContrasenaDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.RecuperacionContrasenaServicio;
import servicios.ValidarEmailServicio;
import utilidades.TokenUtilidad;
import utilidades.EscritorDeLogsUtilidad;

@WebServlet("/enviarRecuperacion")
public class RecuperacionContrasenaControlador extends HttpServlet {

    private RecuperacionContrasenaServicio servicio;
    private ValidarEmailServicio validarEmailServicio;

    @Override
    public void init() throws ServletException {
        servicio = new RecuperacionContrasenaServicio();
        validarEmailServicio = new ValidarEmailServicio();
        EscritorDeLogsUtilidad.registrar("RecuperacionContrasenaControlador - init() - Servicios inicializados");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("RecuperacionContrasenaControlador - doPost() - Inicio de petición");

        String email = request.getParameter("emailUsuario");
        EscritorDeLogsUtilidad.registrar("RecuperacionContrasenaControlador - Parámetro recibido: email=" + email);

        // Validar si el campo email está vacío
        if (email == null || email.isEmpty()) {
            String error = "Introduce un correo válido.";
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RecuperacionContrasenaControlador - ERROR: " + error);
            request.getRequestDispatcher("recuperarContrasena.jsp").forward(request, response);
            return;
        }

        // Validar si el correo está registrado
        if (!validarEmailServicio.emailExiste(email)) {
            String error = "El correo no está registrado.";
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RecuperacionContrasenaControlador - ERROR: " + error);
            request.getRequestDispatcher("recuperarContrasena.jsp").forward(request, response);
            return;
        }

        // Generar token y enviar enlace
        RecuperacionContrasenaDto dto = new RecuperacionContrasenaDto();
        dto.setEmailUsuario(email);

        String token = TokenUtilidad.generarYAsignarTokenRecuperacion(dto);
        EscritorDeLogsUtilidad.registrar("RecuperacionContrasenaControlador - Token generado para email=" + email);

        boolean enviado = servicio.enviarEnlaceRecuperacion(email, token);

        if (enviado) {
            String mensaje = "Se ha enviado un enlace de recuperación al correo: " + email;
            request.setAttribute("successMessage", mensaje);
            EscritorDeLogsUtilidad.registrar("RecuperacionContrasenaControlador - ÉXITO: " + mensaje);
        } else {
            String error = "Error al enviar el correo de recuperación.";
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RecuperacionContrasenaControlador - ERROR: " + error);
        }

        // Redirigir a la misma página para mostrar mensaje
        EscritorDeLogsUtilidad.registrar("RecuperacionContrasenaControlador - Forward a recuperarContrasena.jsp");
        request.getRequestDispatcher("recuperarContrasena.jsp").forward(request, response);
    }
}
