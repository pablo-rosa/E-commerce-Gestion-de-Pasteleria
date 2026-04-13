package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servicios.ActualizarEstadoPedidoServicio;
import utilidades.EscritorDeLogsUtilidad;

import java.io.IOException;

@WebServlet("/modificarEstadoPedido")
public class EstadoPedidoControlador extends HttpServlet {

    private ActualizarEstadoPedidoServicio actualizarEstadoPedidoServicio;

    @Override
    public void init() throws ServletException {
        actualizarEstadoPedidoServicio = new ActualizarEstadoPedidoServicio();
        EscritorDeLogsUtilidad.registrar("EstadoPedidoControlador - init() - Servicio inicializado");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("EstadoPedidoControlador - doPost() - Inicio de petición");

        HttpSession session = request.getSession(false);
        String rol = session != null ? (String) session.getAttribute("rol") : null;

        // Verificación de rol
        if (rol == null || !"admin".equals(rol)) {
            EscritorDeLogsUtilidad.registrar("EstadoPedidoControlador - Rol no autorizado - Redirigiendo a iniciarSesionUsuario.jsp");
            request.setAttribute("errorMensaje", "No tienes permisos para realizar esta acción.");
            request.getRequestDispatcher("iniciarSesionUsuario.jsp").forward(request, response);
            return;
        } else {
            EscritorDeLogsUtilidad.registrar("EstadoPedidoControlador - Rol autorizado");
        }

        try {
            Long idPedido = Long.parseLong(request.getParameter("idPedido"));
            String nuevoEstado = request.getParameter("nuevoEstado");

            EscritorDeLogsUtilidad.registrar("EstadoPedidoControlador - Parámetros recibidos - idPedido=" + idPedido + ", nuevoEstado=" + nuevoEstado);

            // Actualizar estado
            ActualizarEstadoPedidoServicio servicio = new ActualizarEstadoPedidoServicio();
            servicio.actualizarEstadoPedido(idPedido, nuevoEstado);

            // Guardamos mensaje en session para mostrar después del redirect
            session.setAttribute("successMensaje", "Estado actualizado correctamente.");
            EscritorDeLogsUtilidad.registrar("EstadoPedidoControlador - Estado actualizado correctamente para idPedido=" + idPedido);

        } catch (Exception e) {
            EscritorDeLogsUtilidad.registrar("EstadoPedidoControlador - ERROR al modificar estado: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("errorMensaje", "Error al modificar estado: " + e.getMessage());
        }

        EscritorDeLogsUtilidad.registrar("EstadoPedidoControlador - Redirigiendo a /pedidos");
        // Redirigir a /pedidos (GET) para refrescar la lista y evitar 405
        response.sendRedirect(request.getContextPath() + "/pedidos");
    }

}
