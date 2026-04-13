package controladores;

import java.io.IOException;
import dtos.RegistroUsuarioDto;
import servicios.RegistroServicio;
import utilidades.ContrasenaSeguraUtilidad;
import utilidades.EscritorDeLogsUtilidad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registroUsuario")
public class RegistroUsuarioControlador extends HttpServlet {

    private RegistroServicio registroServicio;

    @Override
    public void init() throws ServletException {
        this.registroServicio = new RegistroServicio();
        EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - init() - Servicio de registro inicializado");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - doPost() - Inicio de petición");

        String nombre = request.getParameter("nombreUsuario");
        String telefono = request.getParameter("telefonoUsuario");
        String correo = request.getParameter("emailUsuario");
        String password = request.getParameter("passwordUsuario");
        String confirmPassword = request.getParameter("confirmPasswordUsuario");

        EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - Parámetros recibidos: nombre=" + nombre 
                + ", telefono=" + telefono + ", email=" + correo);

        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            String error = "Las contraseñas no coinciden.";
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - ERROR: " + error);
            request.getRequestDispatcher("registrarseUsuario.jsp").forward(request, response);
            return;
        }

        // Validar seguridad de la contraseña
        if (!ContrasenaSeguraUtilidad.esSegura(password)) {
            String error = "La contraseña debe tener minimo 8 caracteres, 1 mayuscula, 1 minuscula, 1 numero y 1 simbolo especial.";
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - ERROR: " + error);
            request.getRequestDispatcher("registrarseUsuario.jsp").forward(request, response);
            return;
        }

        try {
            // Validar si el email ya existe
            if (registroServicio.emailYaRegistrado(correo)) {
                String error = "El correo ya está registrado.";
                request.setAttribute("errorMessage", error);
                EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - ERROR: " + error);
                request.getRequestDispatcher("registrarseUsuario.jsp").forward(request, response);
                return;
            }

            // Continuar con el registro
            RegistroUsuarioDto registroDto = new RegistroUsuarioDto();
            registroDto.setNombreUsuario(nombre);
            registroDto.setTelefonoUsuario(telefono);
            registroDto.setEmailUsuario(correo);
            registroDto.setPasswordUsuario(password);

            boolean exito = registroServicio.registrarUsuario(registroDto);

            if (exito) {
                EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - Registro exitoso para email=" + correo);
                response.sendRedirect("verificacion.jsp?email=" + correo);
            } else {
                String error = "No se pudo enviar el correo de verificación.";
                request.setAttribute("errorMessage", error);
                EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - ERROR: " + error);
                request.getRequestDispatcher("registrarseUsuario.jsp").forward(request, response);
            }

        } catch (RuntimeException e) {
            String error = "No se pudo conectar al servidor para verificar el email: " + e.getMessage();
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - ERROR RuntimeException: " + error);
            request.getRequestDispatcher("registrarseUsuario.jsp").forward(request, response);

        } catch (Exception e) {
            String error = "Ocurrió un error inesperado: " + e.getMessage();
            request.setAttribute("errorMessage", error);
            EscritorDeLogsUtilidad.registrar("RegistroUsuarioControlador - ERROR Exception: " + error);
            request.getRequestDispatcher("registrarseUsuario.jsp").forward(request, response);
        }
    }
}
