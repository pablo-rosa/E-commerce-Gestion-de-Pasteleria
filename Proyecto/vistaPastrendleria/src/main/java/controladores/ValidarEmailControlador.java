package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.RegistroServicio;
import utilidades.EscritorDeLogsUtilidad;

import java.io.IOException;

@WebServlet("/validarEmail")
public class ValidarEmailControlador extends HttpServlet {

    private RegistroServicio registroServicio;

    @Override
    public void init() throws ServletException {
        registroServicio = new RegistroServicio();
        EscritorDeLogsUtilidad.registrar("ValidarEmailControlador - init() - Servicio de registro inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("ValidarEmailControlador - doGet() - Inicio de petición");

        String email = request.getParameter("email");
        EscritorDeLogsUtilidad.registrar("ValidarEmailControlador - Parámetro recibido: email=" + email);

        if (email == null || email.isEmpty()) {
            EscritorDeLogsUtilidad.registrar("ValidarEmailControlador - ERROR: email vacío");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("false");
            return;
        }

        boolean existe = registroServicio.emailYaRegistrado(email);
        EscritorDeLogsUtilidad.registrar("ValidarEmailControlador - Email ya registrado: " + existe);

        response.setContentType("application/json");
        response.getWriter().write(Boolean.toString(existe));
        EscritorDeLogsUtilidad.registrar("ValidarEmailControlador - Respuesta enviada al frontend");
    }
}
