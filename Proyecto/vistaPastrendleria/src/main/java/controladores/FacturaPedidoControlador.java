package controladores;

import dtos.PedidoDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servicios.ListaPedidoServicio;
import utilidades.FacturaPdfUtilidad;
import utilidades.RolUtilidad;
import utilidades.EscritorDeLogsUtilidad;

import java.io.IOException;

@WebServlet("/facturaPedido")
public class FacturaPedidoControlador extends HttpServlet {

    private ListaPedidoServicio listaPedidoServicio;

    @Override
    public void init() {
        listaPedidoServicio = new ListaPedidoServicio();
        EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - init() - Servicio inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - doGet() - Inicio de petición");

        HttpSession session = request.getSession(false);

        // Seguridad por rol
        if (!RolUtilidad.tieneRol(session, "admin")) {
            EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - Rol no autorizado - Acceso denegado");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
            return;
        } else {
            EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - Rol autorizado");
        }

        try {
            long idPedido = Long.parseLong(request.getParameter("idPedido"));
            EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - Parámetro idPedido=" + idPedido);

            PedidoDto pedido = listaPedidoServicio.obtenerPedidos()
                    .stream()
                    .filter(p -> p.getIdPedido() == idPedido)
                    .findFirst()
                    .orElse(null);

            if (pedido == null) {
                EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - Pedido no encontrado idPedido=" + idPedido);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pedido no encontrado");
                return;
            } else {
                EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - Pedido encontrado idPedido=" + idPedido);
            }

            response.setContentType("application/pdf");
            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename=factura_pedido_" + idPedido + ".pdf"
            );

            EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - Generando PDF de factura");
            FacturaPdfUtilidad.generarFactura(pedido, response.getOutputStream());
            EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - PDF generado correctamente para idPedido=" + idPedido);

        } catch (Exception e) {
            EscritorDeLogsUtilidad.registrar("FacturaPedidoControlador - ERROR generando factura: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generando factura");
        }
    }
}
