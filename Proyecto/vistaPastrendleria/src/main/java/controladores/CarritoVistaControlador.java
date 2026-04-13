package controladores;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dtos.LineaCarritoVistaDto;
import dtos.ListaProductoDto;
import servicios.CarritoServicio;
import servicios.ListaProductoServicio;
import utilidades.EscritorDeLogsUtilidad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para mostrar la vista del carrito.
 * Sigue el patrón MVC donde el controlador solo recibe peticiones
 * y delega la lógica de negocio al servicio.
 */
@WebServlet("/carrito")
public class CarritoVistaControlador extends HttpServlet {

    private final CarritoServicio carritoServicio = new CarritoServicio();
    private final ListaProductoServicio listaProductoServicio = new ListaProductoServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar(
            "CarritoVistaControlador - doGet() - Inicio de petición"
        );

        HttpSession sesion = request.getSession(false);

        try {
            EscritorDeLogsUtilidad.registrar(
                "CarritoVistaControlador - Verificando sesión"
            );

            // Verificar si hay sesión
            if (sesion == null) {
                EscritorDeLogsUtilidad.registrar(
                    "CarritoVistaControlador - Sesión inexistente - Redirigiendo a catalogo"
                );
                response.sendRedirect("catalogo");
                return;
            }

            // ---------- BLOQUE PARA MANEJAR REDIRECCIÓN DE STRIPE ----------
            String accionStripe = request.getParameter("accion");
            EscritorDeLogsUtilidad.registrar(
                "CarritoVistaControlador - Parámetro accionStripe: " + accionStripe
            );

            if ("exito".equals(accionStripe)) {
                EscritorDeLogsUtilidad.registrar(
                    "CarritoVistaControlador - Retorno Stripe EXITO"
                );

                // Solo mostrar mensaje, no volver a confirmar pedido
                sesion.setAttribute("mensajeCarrito", "¡Pago realizado y pedido registrado correctamente!");
                sesion.setAttribute("tipoMensajeCarrito", "exito");

                // Limpiar dirección de sesión si la guardabas
                sesion.removeAttribute("direccionPedido");

                // Redirigir para limpiar parámetros GET
                response.sendRedirect("carrito");
                return;

            } else if ("cancelado".equals(accionStripe)) {
                EscritorDeLogsUtilidad.registrar(
                    "CarritoVistaControlador - Retorno Stripe CANCELADO"
                );

                sesion.setAttribute("mensajeCarrito", "Pago cancelado.");
                sesion.setAttribute("tipoMensajeCarrito", "error");
                response.sendRedirect("carrito");
                return;
            }
            // ---------------------------------------------------------------

            // Obtener ID del usuario de la sesión
            Long idUsuario = (Long) sesion.getAttribute("id");
            EscritorDeLogsUtilidad.registrar(
                "CarritoVistaControlador - idUsuario en sesión: " + idUsuario
            );

            // Obtener carrito de sesión
            Map<Long, Integer> carrito = (Map<Long, Integer>) sesion.getAttribute("carrito");
            EscritorDeLogsUtilidad.registrar(
                "CarritoVistaControlador - Carrito en sesión obtenido"
            );

            // Si el usuario está autenticado, cargar carrito persistente
            if (idUsuario != null) {
                EscritorDeLogsUtilidad.registrar(
                    "CarritoVistaControlador - Usuario autenticado - Cargando carrito persistente"
                );

                carritoServicio.cargarCarritoPersistente(request, idUsuario);
                carrito = (Map<Long, Integer>) sesion.getAttribute("carrito");
            }

            // Preparar datos para la vista
            List<LineaCarritoVistaDto> lineasCarrito = new ArrayList<>();
            BigDecimal totalCarrito = BigDecimal.ZERO;

            EscritorDeLogsUtilidad.registrar(
                "CarritoVistaControlador - Preparando líneas del carrito"
            );

            if (carrito != null && !carrito.isEmpty()) {
                for (Map.Entry<Long, Integer> entry : carrito.entrySet()) {
                    Long idProducto = entry.getKey();
                    Integer cantidad = entry.getValue();

                    EscritorDeLogsUtilidad.registrar(
                        "CarritoVistaControlador - Procesando producto id=" + idProducto +
                        " cantidad=" + cantidad
                    );

                    ListaProductoDto producto = listaProductoServicio.obtenerProductoPorId(idProducto);
                    if (producto == null) {
                        EscritorDeLogsUtilidad.registrar(
                            "CarritoVistaControlador - Producto no encontrado id=" + idProducto
                        );
                        continue;
                    }

                    BigDecimal precioUnitario = producto.getPrecioProducto();
                    BigDecimal totalLinea = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
                    totalCarrito = totalCarrito.add(totalLinea);

                    lineasCarrito.add(new LineaCarritoVistaDto(
                            producto.getIdProducto(),
                            producto.getNombreProducto(),
                            cantidad,
                            precioUnitario,
                            totalLinea
                    ));
                }
            }

            request.setAttribute("lineasCarrito", lineasCarrito);
            request.setAttribute("totalCarrito", totalCarrito);

            // Guardamos el total en sesión para Stripe
            sesion.setAttribute("totalCarrito", totalCarrito);

            request.setAttribute("idUsuario", idUsuario != null ? idUsuario : 0);

            EscritorDeLogsUtilidad.registrar(
                "CarritoVistaControlador - Total carrito calculado: " + totalCarrito
            );

            // Forward al JSP
            request.getRequestDispatcher("carrito.jsp").forward(request, response);

            EscritorDeLogsUtilidad.registrar(
                "CarritoVistaControlador - Forward realizado a carrito.jsp"
            );

        } catch (Exception e) {
            EscritorDeLogsUtilidad.registrar(
                "CarritoVistaControlador - ERROR: " + e.getMessage()
            );
            e.printStackTrace();
            request.setAttribute("errorMensaje", "Error obteniendo el carrito: " + e.getMessage());
            request.getRequestDispatcher("carrito.jsp").forward(request, response);
        }
    }
}
