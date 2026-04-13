package controladores;

import java.io.IOException;
import servicios.ModificarServicio;
import utilidades.RolUtilidad;
import utilidades.EscritorDeLogsUtilidad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/modificarUsuario")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class ModificarUsuarioControlador extends HttpServlet {

    private ModificarServicio modificarServicio;

    @Override
    public void init() throws ServletException {
        this.modificarServicio = new ModificarServicio();
        EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - init() - Servicio inicializado");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - doPost() - Inicio de petición");

        HttpSession sesion = request.getSession(false);

        // Verificar rol
        if (!RolUtilidad.tieneRol(sesion, "admin")) {
            EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - Rol no autorizado - Redirigiendo a iniciarSesionUsuario.jsp");
            response.sendRedirect(request.getContextPath() + "/iniciarSesionUsuario.jsp");
            return;
        } else {
            EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - Rol autorizado: admin");
        }

        try {
            long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
            String nuevoNombre = request.getParameter("nuevoNombre");
            String nuevoTelefono = request.getParameter("nuevoTelefono");
            String nuevoRol = request.getParameter("nuevoRol");

            EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - Parámetros recibidos: idUsuario=" + idUsuario
                    + ", nuevoNombre=" + nuevoNombre + ", nuevoTelefono=" + nuevoTelefono + ", nuevoRol=" + nuevoRol);

            Part fotoPart = request.getPart("nuevaFoto");
            byte[] nuevaFoto = null;
            if (fotoPart != null && fotoPart.getSize() > 0) {
                nuevaFoto = fotoPart.getInputStream().readAllBytes();
                EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - Nueva foto cargada, tamaño: " + nuevaFoto.length + " bytes");
            }

            String resultado = modificarServicio.modificarUsuario(
                    idUsuario, nuevoNombre, nuevoTelefono, nuevoRol, nuevaFoto);

            // Guardar mensaje en sesión
            if (resultado.toLowerCase().contains("error") ||
                resultado.toLowerCase().contains("no se puede") ||
                resultado.toLowerCase().contains("no encontrado")) {

                sesion.setAttribute("errorMensaje", resultado);
                EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - Resultado con error: " + resultado);
            } else {
                sesion.setAttribute("successMensaje", resultado);
                EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - Resultado exitoso: " + resultado);
            }

        } catch (Exception e) {
            EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - ERROR al modificar usuario: " + e.getMessage());
            e.printStackTrace();
            sesion.setAttribute("errorMensaje", "Error al modificar el usuario: " + e.getMessage());
        }

        EscritorDeLogsUtilidad.registrar("ModificarUsuarioControlador - Redirigiendo a /listaUsuarios");
        // Redirigir al controlador principal para refrescar lista y mostrar mensajes
        response.sendRedirect(request.getContextPath() + "/listaUsuarios");
    }
}
