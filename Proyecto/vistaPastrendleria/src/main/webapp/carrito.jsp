<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map, java.util.List, java.math.BigDecimal" %>
<%@ page import="dtos.LineaCarritoVistaDto" %>

<%
    // Obtener datos desde el controlador (atributos de request)
    @SuppressWarnings("unchecked")
    List<LineaCarritoVistaDto> lineasCarrito = (List<LineaCarritoVistaDto>) request.getAttribute("lineasCarrito");
    BigDecimal totalCarrito = (BigDecimal) request.getAttribute("totalCarrito");
    Long idUsuarioSesion = (Long) request.getAttribute("idUsuario");

    // Mensaje de operación del carrito (guardado en sesión por el controlador)
    String mensajeCarrito = (String) session.getAttribute("mensajeCarrito");
    String tipoMensajeCarrito = (String) session.getAttribute("tipoMensajeCarrito");
    if (mensajeCarrito != null) {
        // Limpiar para que no se repita en futuras peticiones
        session.removeAttribute("mensajeCarrito");
        session.removeAttribute("tipoMensajeCarrito");
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Mi Carrito</title>
<link rel="stylesheet" href="estilos/estilos-carrito.css">
<link href="https://fonts.googleapis.com/css2?family=Kalam:wght@700&family=Montserrat:wght@400;600;800&display=swap" rel="stylesheet">
<link rel="icon" type="image/png" href="imagenes/Imagen_favicon.png">
<link rel="stylesheet" href="estilos/estilos-precargavista.css">
<link rel="stylesheet" href="estilos/estilos-navbar.css">
<link rel="stylesheet" href="estilos/estilos-busqueda.css">
<link rel="stylesheet" href="estilos/estilos-listaproductos.css">
<link rel="stylesheet" href="estilos/estilos-ventanas-mensaje.css">
<link href="https://fonts.googleapis.com/css2?family=Baloo+2:wght@500;600&family=Suez+One&display=swap">
</head>
<body>

<!-- PRELOADER -->
<div id="preloader">
    <img src="imagenes/Imagen_favicon.png" alt="Cargando Dulce Sabor">
</div>

<!-- CONTENIDO -->
<div id="content" style="display:none;">
    <!-- NAVBAR -->
    <header class="navbar">
      <div class="nav-left">
        <div class="nav-logo">
          <a href="index.jsp">
            <img src="imagenes/Imagen_favicon.png" alt="Pastrendlería" />
          </a>
        </div>
        <nav>
          <ul>
            <li><a href="index.jsp">Inicio</a></li>
            <li><a href="catalogo">Catálogo</a></li>
            <li><a href="carrito">Carrito de la compra</a></li>
          </ul>
        </nav>
      </div>
      
      <div class="nav-right">
        <div id="searchContainer" class="search-container">
          <input type="text" id="searchInput" class="search-bar" placeholder="Buscar por etiqueta o palabra clave...">
          <div id="liveSearchResults" class="live-search-results"></div>
        </div>
        <div class="icons">
          <%
            String email = (String) session.getAttribute("email");
            String rol = (String) session.getAttribute("rol");

            if (email != null) {
                if ("admin".equals(rol)) {
          %>
                    <a href="menuAdministrador.jsp" class="me-3">Menú Administrador</a>
                    <a href="logout" class="">Cerrar sesión</a>
          <%
                } else {
          %>
                    <a href="mantenimiento.jsp" class="me-3">Cuenta</a>
                    <a href="logout" class="">Cerrar sesión</a>
          <%
                }
            } else {
          %>
                <a href="iniciarSesionUsuario.jsp" class="me-3">Iniciar Sesión</a>
                <a href="registrarseUsuario.jsp">Suscribirse</a>
          <%
            }
          %>
        </div>
      </div>
    </header>
    
    <h1 id="carrito-titulo">Carrito de Compras</h1>

    <!-- Mensaje superior de operaciones de carrito -->
    <div id="mensajeCarrito" style="max-width:800px;margin:0 auto 15px auto;text-align:center;">
        <%
            if (mensajeCarrito != null && !mensajeCarrito.isEmpty()) {
                String cssClass = "alert-success";
                if ("error".equalsIgnoreCase(tipoMensajeCarrito)) {
                    cssClass = "alert-danger";
                }
        %>
            <div class="<%= cssClass %>"><%= mensajeCarrito %></div>
        <%
            }
        %>
    </div>

    <% if (lineasCarrito == null || lineasCarrito.isEmpty()) { %>
        <p style="text-align:center;">Tu carrito está vacío.</p>
    <% } else { %>
        <table>
            <tr>
                <th>Producto</th>
                <th>Cantidad</th>
                <th>Precio Unitario</th>
                <th>Total</th>
                <th>Acciones</th>
            </tr>
            <%
                for (LineaCarritoVistaDto linea : lineasCarrito) {
            %>
            <tr>
                <td><%= linea.getNombreProducto() %></td>
                <td><%= linea.getCantidad() %></td>
                <td><%= linea.getPrecioUnitario() %> €</td>
                <td><%= linea.getTotalLinea() %> €</td>
                <td>
                    <button class="btn btn-agregar" onclick="modificarCarrito('cambiarCantidad', <%=linea.getIdProducto()%>, <%=linea.getCantidad()+1%>)">+1</button>
                    <button class="btn btn-agregar" onclick="modificarCarrito('cambiarCantidad', <%=linea.getIdProducto()%>, <%=linea.getCantidad()-1%>)">-1</button>
                    <button class="btn btn-quitar" onclick="modificarCarrito('eliminar', <%=linea.getIdProducto()%>)">Eliminar</button>
                </td>
            </tr>
            <% } %>
            <tr>
                <td colspan="3" style="text-align:right; font-weight:bold;">Total:</td>
                <td colspan="2" style="font-weight:bold;"><%= totalCarrito %> €</td>
            </tr>
        </table>

        <!-- Campo oculto para enviar el ID de usuario al backend -->
        <input type="hidden" id="idUsuario" name="idUsuario" value="<%= idUsuarioSesion != null ? idUsuarioSesion : 0 %>"/>

        <!-- Campo dirección -->
        <div style="text-align:center; margin-top:10px;">
            <label for="direccion">Dirección de envío:</label><br/>
            <input type="text" id="direccion" name="direccion" placeholder="Calle, Ciudad, CP" style="width:300px; padding:5px; margin-top:5px;"/>
        </div>

        <!-- Botón Confirmar Compra SOLO si hay productos -->
        <div style="text-align:center; margin-top:10px;">
            <button class="btn btn-confirmar" onclick="confirmarCompra()">Confirmar Compra</button>
        </div>
    <% } %>
</div>

<!-- SCRIPTS -->
<script src="javascript/precargavista.js"></script>
<script src="javascript/carrito.js"></script>

<!-- Stripe JS -->
<script src="https://js.stripe.com/v3/"></script>
<script src="javascript/stripe.js"></script>
<script src="https://js.stripe.com/v3/"></script>


</body>
</html>
