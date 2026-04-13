package controladores;

import java.io.IOException;
import servicios.AutentificacionServicio;
import servicios.CarritoServicio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utilidades.EscritorDeLogsUtilidad;

/**
 * Controlador para manejar el inicio de sesión de un usuario.
 * Este servlet procesa solicitudes HTTP POST para autenticar a un usuario,
 * verificar sus credenciales y redirigirlo a la página correspondiente según su rol.
 */
@WebServlet("/loginUsuario")
public class LoginUsuarioControlador extends HttpServlet {

    private AutentificacionServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new AutentificacionServicio();
        EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - init() - Servicio de autenticación inicializado");
    }

    /**
     * Procesa las solicitudes HTTP POST para autenticar un usuario.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - doPost() - Inicio de petición");

        // Recoger los parámetros del formulario
        String correo = request.getParameter("email");
        String password = request.getParameter("password");
        EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - Parámetros recibidos - email=" + correo);

        try {
            // Llamar al servicio para verificar el usuario
            boolean isValidUser = servicio.verificarUsuario(correo, password);

            if (isValidUser) {
                EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - Usuario válido: " + correo);

                // Lógica para manejar el rol y el id y redirigir
                String rol = servicio.getRol();
                long id = servicio.getId();
                HttpSession session = request.getSession();
                session.setAttribute("rol", rol);
                session.setAttribute("id", id);
                session.setAttribute("email", correo);

                EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - Sesión iniciada: id=" + id + ", rol=" + rol);

                // Cargar el carrito persistente
                CarritoServicio carritoServicio = new CarritoServicio();
                carritoServicio.cargarCarritoPersistente(request, id);
                EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - Carrito persistente cargado para usuario id=" + id);

                // Redirigir según rol
                if ("admin".equals(rol)) {
                    EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - Redirigiendo a menuAdministrador.jsp");
                    response.sendRedirect("menuAdministrador.jsp");
                } else if ("usuario".equals(rol)) {
                    EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - Redirigiendo a index.jsp");
                    response.sendRedirect("index.jsp");
                } else {
                    EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - Rol desconocido: " + rol);
                    request.setAttribute("errorMessage", "Rol desconocido.");
                    request.getRequestDispatcher("iniciarSesionUsuario.jsp").forward(request, response);
                }

            } else {
                EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - Credenciales incorrectas para email=" + correo);
                request.setAttribute("errorMessage", "Email o contraseña incorrectos.");
                request.getRequestDispatcher("iniciarSesionUsuario.jsp").forward(request, response); 
            }

        } catch (Exception e) {
            EscritorDeLogsUtilidad.registrar("LoginUsuarioControlador - ERROR en autenticación: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error interno en la autenticación: " + e.getMessage());
            request.getRequestDispatcher("iniciarSesionUsuario.jsp").forward(request, response);
        }
    }
}
