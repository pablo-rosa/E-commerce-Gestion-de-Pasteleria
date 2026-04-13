package controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dtos.ListaProductoDto;
import servicios.ListaProductoServicio;
import utilidades.EscritorDeLogsUtilidad;

import java.util.List;

@Controller
public class ListaProductoControlador {

    @Autowired
    private ListaProductoServicio listaProductoServicio;

    // Ruta para listar todos los productos
    @GetMapping("/productos")
    public String listarProductos(Model model) {
        EscritorDeLogsUtilidad.registrar("ListaProductoControlador - listarProductos() - Inicio de petición");

        List<ListaProductoDto> productos = listaProductoServicio.obtenerProductos();
        model.addAttribute("productos", productos);

        EscritorDeLogsUtilidad.registrar("ListaProductoControlador - listarProductos() - Total productos obtenidos: " + productos.size());
        EscritorDeLogsUtilidad.registrar("ListaProductoControlador - listarProductos() - Preparando vista productos");

        return "productos";  // Vista "productos.html"
    }

    // Ruta para ver un producto específico
    @GetMapping("/producto/{id}")
    public String obtenerProducto(@PathVariable long id, Model model) {
        EscritorDeLogsUtilidad.registrar("ListaProductoControlador - obtenerProducto() - Inicio de petición - idProducto=" + id);

        ListaProductoDto producto = listaProductoServicio.obtenerProductoPorId(id);
        model.addAttribute("producto", producto);

        if (producto != null) {
            EscritorDeLogsUtilidad.registrar("ListaProductoControlador - obtenerProducto() - Producto encontrado: " + producto.getNombreProducto());
        } else {
            EscritorDeLogsUtilidad.registrar("ListaProductoControlador - obtenerProducto() - Producto no encontrado id=" + id);
        }

        return "producto";  // Vista "producto.html"
    }
}
