package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import utilidades.EscritorDeLogsUtilidad;

@WebServlet("/logout")
public class CerrarSesionControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EscritorDeLogsUtilidad.registrar("CerrarSesionControlador - doGet() - Inicio de petición");

        HttpSession session = request.getSession(false);

        if (session != null) {
            EscritorDeLogsUtilidad.registrar("CerrarSesionControlador - Sesión encontrada - Invalida sesión");
            session.invalidate();
        } else {
            EscritorDeLogsUtilidad.registrar("CerrarSesionControlador - No hay sesión activa");
        }

        EscritorDeLogsUtilidad.registrar("CerrarSesionControlador - Redirigiendo a index.jsp");
        response.sendRedirect("index.jsp");
    }
}
