package controladores;

import java.io.IOException;
import servicios.EliminarServicio;
import utilidades.RolUtilidad;
import utilidades.EscritorDeLogsUtilidad;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/eliminarUsuario")
public class EliminarUsuarioControlador extends HttpServlet {

    private final EliminarServicio eliminarServicio = new EliminarServicio();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("EliminarUsuarioControlador - doPost() - Inicio de petición");

        HttpSession sesion = request.getSession(false);

        // Verificar rol
        if (!RolUtilidad.tieneRol(sesion, "admin")) {
            EscritorDeLogsUtilidad.registrar("EliminarUsuarioControlador - Rol no autorizado - Redirigiendo a iniciarSesionUsuario.jsp");
            response.sendRedirect(request.getContextPath() + "/iniciarSesionUsuario.jsp");
            return;
        } else {
            EscritorDeLogsUtilidad.registrar("EliminarUsuarioControlador - Rol autorizado");
        }

        try {
            long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
            String confirmacion = request.getParameter("confirmacionEliminar");

            EscritorDeLogsUtilidad.registrar("EliminarUsuarioControlador - Parámetros recibidos - idUsuario=" + idUsuario + " confirmacion=" + confirmacion);

            String resultado = eliminarServicio.eliminarUsuario(idUsuario, confirmacion);

            // Guardar mensaje en sesión
            if (resultado.toLowerCase().contains("error") ||
                resultado.toLowerCase().contains("no se puede") ||
                resultado.toLowerCase().contains("no encontrado") ||
                resultado.toLowerCase().contains("confirmación no coincide")) {

                sesion.setAttribute("errorMensaje", resultado);
                EscritorDeLogsUtilidad.registrar("EliminarUsuarioControlador - Resultado: ERROR - " + resultado);
            } else {
                sesion.setAttribute("successMensaje", resultado);
                EscritorDeLogsUtilidad.registrar("EliminarUsuarioControlador - Resultado: ÉXITO - " + resultado);
            }

        } catch (Exception e) {
            EscritorDeLogsUtilidad.registrar("EliminarUsuarioControlador - EXCEPCIÓN: " + e.getMessage());
            e.printStackTrace();
            sesion.setAttribute("errorMensaje", "Error al eliminar el usuario: " + e.getMessage());
        }

        EscritorDeLogsUtilidad.registrar("EliminarUsuarioControlador - Redirigiendo a /listaUsuarios");
        // Redirigir al controlador principal para refrescar lista y mostrar mensajes
        response.sendRedirect(request.getContextPath() + "/listaUsuarios");
    }
}
