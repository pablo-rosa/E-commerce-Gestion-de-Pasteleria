package controladores;

import dtos.PedidoDto;
import servicios.ListaPedidoServicio;
import utilidades.RolUtilidad;
import utilidades.EscritorDeLogsUtilidad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/pedidos")
public class ListaPedidoControlador extends HttpServlet {

    private ListaPedidoServicio listaPedidoServicio;

    @Override
    public void init() throws ServletException {
        listaPedidoServicio = new ListaPedidoServicio();
        EscritorDeLogsUtilidad.registrar("ListaPedidoControlador - init() - Servicio inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("ListaPedidoControlador - doGet() - Inicio de petición");

        HttpSession session = request.getSession(false);

        // Verificación de rol
        if (!RolUtilidad.tieneRol(session, "admin")) {
            EscritorDeLogsUtilidad.registrar("ListaPedidoControlador - Rol no autorizado - Redirigiendo a iniciarSesionUsuario.jsp");
            request.setAttribute("errorMensaje", "No tienes permisos para acceder a esta página.");
            request.getRequestDispatcher("iniciarSesionUsuario.jsp").forward(request, response);
            return;
        } else {
            EscritorDeLogsUtilidad.registrar("ListaPedidoControlador - Rol autorizado");
        }

        // Obtener pedidos
        EscritorDeLogsUtilidad.registrar("ListaPedidoControlador - Obteniendo lista de pedidos");
        List<PedidoDto> pedidos = listaPedidoServicio.obtenerPedidos();
        request.setAttribute("pedidos", pedidos);
        EscritorDeLogsUtilidad.registrar("ListaPedidoControlador - Total de pedidos obtenidos: " + pedidos.size());

        // Mostrar mensajes si existen
        String successMensaje = (String) session.getAttribute("successMensaje");
        String errorMensaje = (String) session.getAttribute("errorMensaje");

        if (successMensaje != null) {
            request.setAttribute("successMensaje", successMensaje);
            session.removeAttribute("successMensaje");
            EscritorDeLogsUtilidad.registrar("ListaPedidoControlador - Mensaje de éxito enviado a la vista: " + successMensaje);
        }

        if (errorMensaje != null) {
            request.setAttribute("errorMensaje", errorMensaje);
            session.removeAttribute("errorMensaje");
            EscritorDeLogsUtilidad.registrar("ListaPedidoControlador - Mensaje de error enviado a la vista: " + errorMensaje);
        }

        // Forward al JSP
        request.getRequestDispatcher("pedidos.jsp").forward(request, response);
        EscritorDeLogsUtilidad.registrar("ListaPedidoControlador - Forward realizado a pedidos.jsp");
    }
}
