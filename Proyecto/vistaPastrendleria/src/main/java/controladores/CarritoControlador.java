package controladores;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servicios.CarritoServicio;
import utilidades.EscritorDeLogsUtilidad;

import java.io.PrintWriter;
import java.io.IOException;

import org.json.JSONObject;

/**
 * Controlador que maneja todas las acciones del carrito:
 * agregar, eliminar, cambiar cantidad y confirmar compra.
 * Responde con JSON para ser consumido por AJAX en el frontend.
 * Sigue el patrón MVC donde el controlador solo recibe peticiones
 * y delega la lógica de negocio al servicio.
 */
@WebServlet("/CarritoControlador")
public class CarritoControlador extends HttpServlet {

    /**
     * Servicio que contiene la lógica de negocio del carrito.
     * Utiliza Spring Boot para las dependencias.
     */
    private final CarritoServicio carritoServicio = new CarritoServicio();

    /**
     * Procesa las peticiones POST para gestionar el carrito.
     * 
     * @param request HttpServletRequest con los datos de la petición
     * @param response HttpServletResponse para enviar la respuesta JSON
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EscritorDeLogsUtilidad.registrar(
            "CarritoControlador - doPost() - Inicio de petición"
        );

        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession();

        try {
            EscritorDeLogsUtilidad.registrar(
                "CarritoControlador - Leyendo cuerpo JSON de la petición"
            );

            // Leer cuerpo JSON de la petición
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }

            JSONObject json = new JSONObject(sb.toString());

            String accion = json.getString("accion");
            long idProducto = json.has("idProducto") ? json.getLong("idProducto") : 0;
            int cantidad = json.has("cantidad") ? json.getInt("cantidad") : 0;

            EscritorDeLogsUtilidad.registrar(
                "CarritoControlador - Acción recibida: " + accion
            );

            JSONObject jsonResponse = new JSONObject();

            // Delegar la lógica al servicio según la acción
            switch(accion) {
                case "agregar":
                    {
                        EscritorDeLogsUtilidad.registrar(
                            "CarritoControlador - Acción AGREGAR - idProducto=" + idProducto
                        );

                        String mensaje = carritoServicio.agregarProducto(request, idProducto);
                        jsonResponse.put("mensaje", mensaje);
                        session.setAttribute("mensajeCarrito", mensaje);
                        session.setAttribute("tipoMensajeCarrito", "exito");
                    }
                    break;
                case "eliminar":
                    {
                        EscritorDeLogsUtilidad.registrar(
                            "CarritoControlador - Acción ELIMINAR - idProducto=" + idProducto
                        );

                        String mensaje = carritoServicio.eliminarProducto(request, idProducto);
                        jsonResponse.put("mensaje", mensaje);
                        session.setAttribute("mensajeCarrito", mensaje);
                        session.setAttribute("tipoMensajeCarrito", "exito");
                    }
                    break;
                case "cambiarCantidad":
                    {
                        EscritorDeLogsUtilidad.registrar(
                            "CarritoControlador - Acción CAMBIAR CANTIDAD - idProducto="
                            + idProducto + " cantidad=" + cantidad
                        );

                        String mensaje = carritoServicio.cambiarCantidad(request, idProducto, cantidad);
                        jsonResponse.put("mensaje", mensaje);
                        session.setAttribute("mensajeCarrito", mensaje);
                        session.setAttribute("tipoMensajeCarrito", "exito");
                    }
                    break;
                case "confirmar":
                    EscritorDeLogsUtilidad.registrar(
                        "CarritoControlador - Acción CONFIRMAR pedido"
                    );

                    String direccionPedido = json.has("direccionPedido") ? json.getString("direccionPedido") : "";
                    long idUsuario = json.has("idUsuario") ? json.getLong("idUsuario") : 0;

                    EscritorDeLogsUtilidad.registrar(
                        "CarritoControlador - Datos confirmación - idUsuario=" + idUsuario
                    );

                    // Validación de sesión antes de confirmar compra
                    if(idUsuario <= 0) {
                        EscritorDeLogsUtilidad.registrar(
                            "CarritoControlador - Confirmación fallida - Usuario no autenticado"
                        );

                        String mensaje = "No hay ninguna cuenta iniciada. Inicia sesión para poder comprar.";
                        jsonResponse.put("mensaje", mensaje);
                        session.setAttribute("mensajeCarrito", mensaje);
                        session.setAttribute("tipoMensajeCarrito", "error");
                    } else {
                        String mensaje = carritoServicio.confirmarCompra(request, direccionPedido, idUsuario);
                        jsonResponse.put("mensaje", mensaje);

                        String tipo = (mensaje != null &&
                                      (mensaje.toLowerCase().contains("correctamente")
                                    || mensaje.toLowerCase().contains("registrado")))
                                      ? "exito" : "error";

                        session.setAttribute("mensajeCarrito", mensaje);
                        session.setAttribute("tipoMensajeCarrito", tipo);

                        EscritorDeLogsUtilidad.registrar(
                            "CarritoControlador - Confirmación procesada - Resultado: " + tipo
                        );
                    }
                    break;
                default:
                    {
                        EscritorDeLogsUtilidad.registrar(
                            "CarritoControlador - Acción no reconocida"
                        );

                        String mensaje = "Acción no reconocida.";
                        jsonResponse.put("mensaje", mensaje);
                        session.setAttribute("mensajeCarrito", mensaje);
                        session.setAttribute("tipoMensajeCarrito", "error");
                    }
                    break;
            }

            // Enviar respuesta JSON
            EscritorDeLogsUtilidad.registrar(
                "CarritoControlador - Enviando respuesta JSON"
            );

            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
            out.flush();

            EscritorDeLogsUtilidad.registrar(
                "CarritoControlador - Petición finalizada correctamente"
            );
            
        } catch (Exception e) {
            EscritorDeLogsUtilidad.registrar(
                "CarritoControlador - ERROR en doPost(): " + e.getMessage()
            );

            // Manejo de errores
            JSONObject jsonResponse = new JSONObject();
            String mensaje = "Error al procesar la petición: " + e.getMessage();
            jsonResponse.put("mensaje", mensaje);
            session.setAttribute("mensajeCarrito", mensaje);
            session.setAttribute("tipoMensajeCarrito", "error");
            
            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
            out.flush();
            
            System.out.println("Error en CarritoControlador: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
