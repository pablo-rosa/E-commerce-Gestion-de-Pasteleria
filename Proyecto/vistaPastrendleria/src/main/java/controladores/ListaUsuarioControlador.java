package controladores;

import java.io.IOException;
import java.util.List;

import dtos.ListaUsuarioDto;
import servicios.ListaUsuarioServicio;
import utilidades.EscritorDeLogsUtilidad;
import utilidades.RolUtilidad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/listaUsuarios")
public class ListaUsuarioControlador extends HttpServlet {

    private final ListaUsuarioServicio listaUsuarioServicio = new ListaUsuarioServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - doGet() - Inicio de petición");

        HttpSession sesion = request.getSession(false);

        try {
            EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - Verificando rol de usuario");

            // Verificar si el usuario tiene rol admin
            if (!RolUtilidad.tieneRol(sesion, "admin")) {
                EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - Acceso denegado: Usuario sin permisos");
                response.sendRedirect(request.getContextPath() + "/iniciarSesionUsuario.jsp");
                return;
            } else {
                EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - Rol autorizado: admin");
            }

            // Obtener lista actualizada de usuarios
            refrescarListaUsuarios(request, sesion);
            EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - Lista de usuarios obtenida. Total: " 
                + (request.getAttribute("usuarios") != null 
                    ? ((List<ListaUsuarioDto>) request.getAttribute("usuarios")).size() 
                    : 0));

            // Forward al JSP
            request.getRequestDispatcher("listaUsuarios.jsp").forward(request, response);
            EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - Forward realizado a listaUsuarios.jsp");

        } catch (Exception e) {
            EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - ERROR: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMensaje", "Error obteniendo usuarios: " + e.getMessage());
            request.getRequestDispatcher("listaUsuarios.jsp").forward(request, response);
        }
    }

    /**
     * Refresca la lista de usuarios y los mensajes de sesión
     */
    private void refrescarListaUsuarios(HttpServletRequest request, HttpSession sesion) {
        EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - refrescarListaUsuarios() - Inicio");

        // Obtener la lista de usuarios desde el servicio
        List<ListaUsuarioDto> usuarios = listaUsuarioServicio.obtenerUsuarios();
        request.setAttribute("usuarios", usuarios);
        EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - Usuarios cargados: " + usuarios.size());

        // Cargar mensajes desde sesión si existen
        if (sesion != null) {
            String successMensaje = (String) sesion.getAttribute("successMensaje");
            String errorMensaje = (String) sesion.getAttribute("errorMensaje");

            if (successMensaje != null) {
                request.setAttribute("successMensaje", successMensaje);
                sesion.removeAttribute("successMensaje");
                EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - Mensaje de éxito enviado a la vista: " + successMensaje);
            }

            if (errorMensaje != null) {
                request.setAttribute("errorMensaje", errorMensaje);
                sesion.removeAttribute("errorMensaje");
                EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - Mensaje de error enviado a la vista: " + errorMensaje);
            }
        }

        EscritorDeLogsUtilidad.registrar("ListaUsuarioControlador - refrescarListaUsuarios() - Fin");
    }
}
