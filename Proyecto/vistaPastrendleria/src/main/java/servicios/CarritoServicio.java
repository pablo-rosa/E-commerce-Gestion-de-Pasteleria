package servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

import dtos.CarritoDto;
import dtos.DetalleCarritoDto;
import dtos.ListaProductoDto;
import dtos.AñadirDetalleCarritoDto;
import dtos.AñadirPedidoDto;
import dtos.AñadirDetallePedidoDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio que gestiona la lógica de negocio del carrito.
 * Este servicio se comunica con la API para persistir el carrito
 * y también mantiene el carrito en sesión para usuarios no autenticados.
 * Sigue el patrón MVC donde el servicio contiene la lógica de negocio
 * y utiliza Spring Boot para las dependencias.
 */
@Service
public class CarritoServicio {

    private final String API_URL_BASE = "http://localhost:8081/api/carrito";
    private final String API_URL_PEDIDOS = "http://localhost:8081/api/pedidos/registrar";
    private final ListaProductoServicio productoServicio;

    /**
     * Constructor que inicializa el servicio de productos.
     */
    public CarritoServicio() {
        this.productoServicio = new ListaProductoServicio();
    }

    /**
     * Obtiene el carrito persistente de un usuario desde la API.
     * 
     * @param idUsuario ID del usuario
     * @return CarritoDto con los datos del carrito o null si no existe
     */
    public CarritoDto obtenerCarritoPersistente(Long idUsuario) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = API_URL_BASE + "/usuario/" + idUsuario;
            CarritoDto carrito = restTemplate.getForObject(url, CarritoDto.class);
            return carrito;
        } catch (Exception e) {
            System.out.println("Error al obtener carrito persistente: " + e.getMessage());
            return null;
        }
    }

    /**
     * Carga el carrito persistente en la sesión del usuario.
     * Si el usuario tiene productos en sesión, los sincroniza con el carrito persistente.
     * 
     * @param request HttpServletRequest para acceder a la sesión
     * @param idUsuario ID del usuario autenticado
     */
    public void cargarCarritoPersistente(HttpServletRequest request, Long idUsuario) {
        HttpSession session = request.getSession();

        // Evitar sincronizaciones repetidas que duplican cantidades:
        // si ya hemos sincronizado una vez esta sesión con el carrito persistente,
        // simplemente reflejamos el carrito de BD en sesión y salimos.
        Boolean carritoSincronizado = (Boolean) session.getAttribute("carritoSincronizado");
        if (Boolean.TRUE.equals(carritoSincronizado)) {
            CarritoDto carritoPersistente = obtenerCarritoPersistente(idUsuario);
            if (carritoPersistente != null && carritoPersistente.getDetalles() != null) {
                Map<Long, Integer> carritoMap = new HashMap<>();
                for (DetalleCarritoDto detalle : carritoPersistente.getDetalles()) {
                    carritoMap.put(detalle.getIdProducto(), detalle.getCantidad());
                }
                session.setAttribute("carrito", carritoMap);
            }
            return;
        }
        
        // Obtener carrito persistente de la API
        CarritoDto carritoPersistente = obtenerCarritoPersistente(idUsuario);
        
        // Obtener carrito de sesión (si existe)
        Map<Long, Integer> carritoSesion = (Map<Long, Integer>) session.getAttribute("carrito");
        
        if (carritoPersistente != null && carritoPersistente.getDetalles() != null) {
            // Convertir carrito persistente a formato de sesión
            Map<Long, Integer> carritoMap = new HashMap<>();
            for (DetalleCarritoDto detalle : carritoPersistente.getDetalles()) {
                carritoMap.put(detalle.getIdProducto(), detalle.getCantidad());
            }
            
            // Si hay carrito de sesión, sincronizar
            if (carritoSesion != null && !carritoSesion.isEmpty()) {
                sincronizarCarritos(idUsuario, carritoSesion);
                // Consumir el carrito de sesión para evitar re-sincronizaciones y duplicados
                session.removeAttribute("carrito");
                // Después de sincronizar, recargar el carrito persistente
                carritoPersistente = obtenerCarritoPersistente(idUsuario);
                carritoMap = new HashMap<>();
                for (DetalleCarritoDto detalle : carritoPersistente.getDetalles()) {
                    carritoMap.put(detalle.getIdProducto(), detalle.getCantidad());
                }
            }
            
            // Actualizar sesión con el carrito persistente
            session.setAttribute("carrito", carritoMap);
            // Marcar como sincronizado para no volver a sumar el carrito de sesión
            session.setAttribute("carritoSincronizado", Boolean.TRUE);
        } else if (carritoSesion != null && !carritoSesion.isEmpty()) {
            // Si no hay carrito persistente pero hay carrito de sesión, sincronizar
            sincronizarCarritos(idUsuario, carritoSesion);
            // Después de sincronizar, reflejar el carrito persistente en sesión y marcarlo
            actualizarCarritoEnSesion(session, idUsuario);
            session.setAttribute("carritoSincronizado", Boolean.TRUE);
        }
    }

    /**
     * Sincroniza el carrito de sesión con el carrito persistente.
     * 
     * @param idUsuario ID del usuario
     * @param carritoSesion Carrito almacenado en sesión
     */
    private void sincronizarCarritos(Long idUsuario, Map<Long, Integer> carritoSesion) {
        try {
            // Convertir carrito de sesión a lista de DTOs
            List<AñadirDetalleCarritoDto> detallesSesion = new ArrayList<>();
            for (Map.Entry<Long, Integer> entry : carritoSesion.entrySet()) {
                ListaProductoDto producto = productoServicio.obtenerProductoPorId(entry.getKey());
                if (producto != null) {
                    AñadirDetalleCarritoDto detalle = new AñadirDetalleCarritoDto();
                    detalle.setIdProducto(entry.getKey());
                    detalle.setNombreProducto(producto.getNombreProducto());
                    detalle.setCantidad(entry.getValue());
                    detalle.setPrecioUnitario(producto.getPrecioProducto());
                    detallesSesion.add(detalle);
                }
            }
            
            // Llamar a la API para sincronizar
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Gson gson = new Gson();
            String json = gson.toJson(detallesSesion);
            
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            String url = API_URL_BASE + "/sincronizar/" + idUsuario;
            
            restTemplate.postForObject(url, entity, CarritoDto.class);
        } catch (Exception e) {
            System.out.println("Error al sincronizar carritos: " + e.getMessage());
        }
    }

    /**
     * Añade un producto al carrito.
     * Si el usuario está autenticado, persiste en la base de datos.
     * Si no, solo lo guarda en sesión.
     * 
     * @param request HttpServletRequest para acceder a la sesión
     * @param idProducto ID del producto a añadir
     * @return Mensaje de confirmación
     */
    public String agregarProducto(HttpServletRequest request, long idProducto) {
        HttpSession session = request.getSession();
        Long idUsuario = (Long) session.getAttribute("id");
        
        // Obtener información del producto
        ListaProductoDto producto = productoServicio.obtenerProductoPorId(idProducto);
        if (producto == null) {
            return "Producto no encontrado.";
        }
        
        if (idUsuario != null) {
            // Usuario autenticado: persistir en BD
            try {
                AñadirDetalleCarritoDto detalleDto = new AñadirDetalleCarritoDto();
                detalleDto.setIdProducto(idProducto);
                detalleDto.setNombreProducto(producto.getNombreProducto());
                detalleDto.setCantidad(1);
                detalleDto.setPrecioUnitario(producto.getPrecioProducto());
                
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                
                Gson gson = new Gson();
                String json = gson.toJson(detalleDto);
                
                HttpEntity<String> entity = new HttpEntity<>(json, headers);
                String url = API_URL_BASE + "/añadir/" + idUsuario;
                
                restTemplate.postForObject(url, entity, CarritoDto.class);
                
                // Actualizar carrito en sesión
                actualizarCarritoEnSesion(session, idUsuario);
                
                return producto.getNombreProducto() + " agregado al carrito.";
            } catch (Exception e) {
                System.out.println("Error al añadir producto al carrito persistente: " + e.getMessage());
                // Fallback a sesión
                return agregarProductoASesion(session, idProducto, producto);
            }
        } else {
            // Usuario no autenticado: solo sesión
            return agregarProductoASesion(session, idProducto, producto);
        }
    }

    /**
     * Añade un producto al carrito de sesión.
     * 
     * @param session Sesión HTTP
     * @param idProducto ID del producto
     * @param producto DTO del producto
     * @return Mensaje de confirmación
     */
    private String agregarProductoASesion(HttpSession session, long idProducto, ListaProductoDto producto) {
        Map<Long, Integer> carrito = (Map<Long, Integer>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new HashMap<>();
        }
        carrito.put(idProducto, carrito.getOrDefault(idProducto, 0) + 1);
        session.setAttribute("carrito", carrito);
        return producto.getNombreProducto() + " agregado al carrito.";
    }

    /**
     * Elimina un producto del carrito.
     * 
     * @param request HttpServletRequest para acceder a la sesión
     * @param idProducto ID del producto a eliminar
     * @return Mensaje de confirmación
     */
    public String eliminarProducto(HttpServletRequest request, long idProducto) {
        HttpSession session = request.getSession();
        Long idUsuario = (Long) session.getAttribute("id");
        
        if (idUsuario != null) {
            // Usuario autenticado: eliminar de BD
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = API_URL_BASE + "/eliminar/" + idUsuario + "/" + idProducto;
                restTemplate.delete(url);
                
                // Actualizar carrito en sesión
                actualizarCarritoEnSesion(session, idUsuario);
                
                return "Producto eliminado del carrito.";
            } catch (Exception e) {
                System.out.println("Error al eliminar producto del carrito persistente: " + e.getMessage());
                // Fallback a sesión
                return eliminarProductoDeSesion(session, idProducto);
            }
        } else {
            // Usuario no autenticado: solo sesión
            return eliminarProductoDeSesion(session, idProducto);
        }
    }

    /**
     * Elimina un producto del carrito de sesión.
     * 
     * @param session Sesión HTTP
     * @param idProducto ID del producto
     * @return Mensaje de confirmación
     */
    private String eliminarProductoDeSesion(HttpSession session, long idProducto) {
        Map<Long, Integer> carrito = (Map<Long, Integer>) session.getAttribute("carrito");
        if (carrito != null) {
            carrito.remove(idProducto);
            session.setAttribute("carrito", carrito);
        }
        return "Producto eliminado del carrito.";
    }

    /**
     * Cambia la cantidad de un producto en el carrito.
     * 
     * @param request HttpServletRequest para acceder a la sesión
     * @param idProducto ID del producto
     * @param cantidad Nueva cantidad
     * @return Mensaje de confirmación
     */
    public String cambiarCantidad(HttpServletRequest request, long idProducto, int cantidad) {
        HttpSession session = request.getSession();
        Long idUsuario = (Long) session.getAttribute("id");
        
        if (idUsuario != null) {
            // Usuario autenticado: actualizar en BD
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = API_URL_BASE + "/actualizar/" + idUsuario + "/" + idProducto + "?cantidad=" + cantidad;
                restTemplate.put(url, null);
                
                // Actualizar carrito en sesión
                actualizarCarritoEnSesion(session, idUsuario);
                
                return "Cantidad actualizada.";
            } catch (Exception e) {
                System.out.println("Error al actualizar cantidad en carrito persistente: " + e.getMessage());
                // Fallback a sesión
                return cambiarCantidadEnSesion(session, idProducto, cantidad);
            }
        } else {
            // Usuario no autenticado: solo sesión
            return cambiarCantidadEnSesion(session, idProducto, cantidad);
        }
    }

    /**
     * Cambia la cantidad de un producto en el carrito de sesión.
     * 
     * @param session Sesión HTTP
     * @param idProducto ID del producto
     * @param cantidad Nueva cantidad
     * @return Mensaje de confirmación
     */
    private String cambiarCantidadEnSesion(HttpSession session, long idProducto, int cantidad) {
        Map<Long, Integer> carrito = (Map<Long, Integer>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new HashMap<>();
        }
        if (cantidad <= 0) {
            carrito.remove(idProducto);
        } else {
            carrito.put(idProducto, cantidad);
        }
        session.setAttribute("carrito", carrito);
        return "Cantidad actualizada.";
    }

    /**
     * Confirma la compra convirtiendo el carrito en un pedido.
     * 
     * @param request HttpServletRequest para acceder a la sesión
     * @param direccionPedido Dirección de entrega
     * @param idUsuario ID del usuario
     * @return Mensaje de confirmación
     */
    public String confirmarCompra(HttpServletRequest request, String direccionPedido, long idUsuario) {
        HttpSession session = request.getSession();
        Map<Long, Integer> carrito = (Map<Long, Integer>) session.getAttribute("carrito");
        
        if (carrito == null || carrito.isEmpty()) {
            return "Carrito vacío.";
        }

        if (idUsuario <= 0) {
            return "Debes iniciar sesión para confirmar la compra.";
        }

        if (direccionPedido == null || direccionPedido.trim().isEmpty()) {
            return "Debes introducir una dirección.";
        }

        try {
            // Obtener carrito persistente
            CarritoDto carritoPersistente = obtenerCarritoPersistente(idUsuario);
            
            // Crear DTO de pedido
            AñadirPedidoDto pedidoDto = new AñadirPedidoDto();
            pedidoDto.setIdUsuario(idUsuario);
            pedidoDto.setDireccionPedido(direccionPedido);

            List<AñadirDetallePedidoDto> detalles = new ArrayList<>();
            
            // Usar carrito persistente si existe, sino usar carrito de sesión
            if (carritoPersistente != null && carritoPersistente.getDetalles() != null) {
                for (DetalleCarritoDto detalle : carritoPersistente.getDetalles()) {
                    AñadirDetallePedidoDto detallePedido = new AñadirDetallePedidoDto();
                    detallePedido.setIdProducto(detalle.getIdProducto());
                    detallePedido.setNombreProducto(detalle.getNombreProducto());
                    detallePedido.setCantidad(detalle.getCantidad());
                    detallePedido.setPrecioUnitario(detalle.getPrecioUnitario());
                    detalles.add(detallePedido);
                }
            } else {
                // Fallback a carrito de sesión
                for (Map.Entry<Long, Integer> entry : carrito.entrySet()) {
                    ListaProductoDto p = productoServicio.obtenerProductoPorId(entry.getKey());
                    if (p == null) continue;

                    AñadirDetallePedidoDto detalle = new AñadirDetallePedidoDto();
                    detalle.setIdProducto(p.getIdProducto());
                    detalle.setNombreProducto(p.getNombreProducto());
                    detalle.setCantidad(entry.getValue());
                    detalle.setPrecioUnitario(p.getPrecioProducto());
                    detalles.add(detalle);
                }
            }
            
            if (detalles.isEmpty()) {
                return "El carrito está vacío o hay productos inválidos.";
            }
            
            pedidoDto.setDetalles(detalles);

            // Enviar pedido a la API
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Gson gson = new Gson();
            String json = gson.toJson(pedidoDto);
            
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL_PEDIDOS, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Cerrar carrito persistente en la API
                try {
                    String urlCerrar = API_URL_BASE + "/cerrar/" + idUsuario;
                    restTemplate.put(urlCerrar, null);
                } catch (Exception e) {
                    // Si falla el cierre del carrito, lo registramos pero no rompemos la compra
                    System.out.println("Error al cerrar el carrito persistente: " + e.getMessage());
                }

                // Limpiar carrito en sesión
                carrito.clear();
                session.setAttribute("carrito", carrito);
                return "¡Pedido registrado correctamente!";
            } else {
                return "Error al registrar el pedido. Código: " + response.getStatusCode();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar el pedido: " + e.getMessage();
        }
    }

    /**
     * Actualiza el carrito en sesión con los datos del carrito persistente.
     * 
     * @param session Sesión HTTP
     * @param idUsuario ID del usuario
     */
    private void actualizarCarritoEnSesion(HttpSession session, Long idUsuario) {
        CarritoDto carritoPersistente = obtenerCarritoPersistente(idUsuario);
        if (carritoPersistente != null && carritoPersistente.getDetalles() != null) {
            Map<Long, Integer> carritoMap = new HashMap<>();
            for (DetalleCarritoDto detalle : carritoPersistente.getDetalles()) {
                carritoMap.put(detalle.getIdProducto(), detalle.getCantidad());
            }
            session.setAttribute("carrito", carritoMap);
        }
    }
}
