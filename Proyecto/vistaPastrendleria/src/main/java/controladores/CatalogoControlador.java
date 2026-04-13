package controladores;

import java.io.IOException;
import java.util.List;

import dtos.ListaProductoDto;
import servicios.ListaProductoServicio;
import utilidades.EscritorDeLogsUtilidad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para manejar las peticiones del catálogo de productos.
 * Sigue el patrón MVC donde el controlador solo recibe peticiones
 * y delega la lógica de negocio al servicio.
 */
@WebServlet("/catalogo")
public class CatalogoControlador extends HttpServlet {

    /**
     * Servicio que contiene la lógica de negocio para obtener productos.
     * Utiliza Spring Boot para las dependencias.
     */
    private final ListaProductoServicio listaProductoServicio = new ListaProductoServicio();

    /**
     * Procesa las peticiones GET para mostrar el catálogo de productos.
     * 
     * @param request HttpServletRequest con los datos de la petición
     * @param response HttpServletResponse para enviar la respuesta
     * @throws ServletException si ocurre un error relacionado con el servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("CatalogoControlador - doGet() - Inicio de petición");

        try {
            EscritorDeLogsUtilidad.registrar("CatalogoControlador - Obteniendo lista de productos");

            // Obtener lista de productos desde el servicio
            List<ListaProductoDto> listaProductos = listaProductoServicio.obtenerProductos();

            EscritorDeLogsUtilidad.registrar("CatalogoControlador - Lista de productos obtenida. Total: " + listaProductos.size());

            // Pasar la lista de productos a la vista
            request.setAttribute("listaProductos", listaProductos);

            // Forward al JSP
            request.getRequestDispatcher("catalogo.jsp").forward(request, response);

            EscritorDeLogsUtilidad.registrar("CatalogoControlador - Lista de productos enviada a catalogo.jsp");

        } catch (Exception e) {
            EscritorDeLogsUtilidad.registrar("CatalogoControlador - ERROR: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMensaje", "Error obteniendo productos: " + e.getMessage());
            request.getRequestDispatcher("catalogo.jsp").forward(request, response);
        }
    }
}
