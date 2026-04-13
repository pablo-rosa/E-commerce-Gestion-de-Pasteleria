<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, dtos.ListaProductoDto" %>
<%
    // Obtener la lista de productos desde el controlador (atributo de request)
    List<ListaProductoDto> listaProductos = (List<ListaProductoDto>) request.getAttribute("listaProductos");
    
if (listaProductos == null || listaProductos.isEmpty()) {
}

%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Catálogo de Productos</title>
    <link rel="stylesheet" href="estilos/style.css">
  <link href="https://fonts.googleapis.com/css2?family=Kalam:wght@700&family=Montserrat:wght@400;600;800&display=swap"
    rel="stylesheet">
  <!-- ✅ FAVICON -->
  <link rel="icon" type="image/png" href="imagenes/Imagen_favicon.png">
  <!-- Enlace al archivo CSS de la precarga -->
  <link rel="stylesheet" href="estilos/estilos-precargavista.css">
  <link rel="stylesheet" href="estilos/estilos-navbar.css">
  <link rel="stylesheet" href="estilos/estilos-busqueda.css">
  <link rel="stylesheet" href="estilos/estilos-listaproductos.css">
  <link rel="stylesheet" href="estilos/estilos-ventanas-mensaje.css">
  <link href="https://fonts.googleapis.com/css2?family=Baloo+2:wght@500;600&family=Suez+One&display=swap">
  
<style>
    .catalogo { display:flex; flex-wrap: wrap; justify-content: center; padding:20px; gap:20px; }
    .producto { background:#fff; border-radius:8px; box-shadow:0 2px 5px rgba(0,0,0,0.2); width:220px; padding:15px; display:flex; flex-direction:column; align-items:center; }
    .producto img { width:180px; height:180px; object-fit:cover; border-radius:5px; margin-bottom:10px; }
    .producto h3 { margin:5px 0; font-size:18px; text-align:center; }
    .producto p { margin:5px 0; font-size:14px; text-align:center; }
    .producto .precio { font-weight:bold; margin:10px 0; }
    .producto button { padding:8px 12px; background:#28a745; color:#fff; border:none; border-radius:5px; cursor:pointer; }
    .producto button:hover { background:#218838; }
</style>
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
                <a href="index.jsp"> <!-- Aquí envolvemos la imagen en un enlace -->
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
            <!-- 🔍 Contenedor del buscador funcional -->
            <div id="searchContainer" class="search-container">
                <input type="text" id="searchInput" class="search-bar" placeholder="Buscar por etiqueta o palabra clave...">
                <div id="liveSearchResults" class="live-search-results"></div>
            </div>
            <div class="icons">
                <!-- Verificar si el usuario está logueado -->
                <%
    String email = (String) session.getAttribute("email");
    String rol = (String) session.getAttribute("rol");

    if (email != null) {
        if ("admin".equals(rol)) {
            // Si es administrador, mostrar menú admin
%>
            <a href="menuAdministrador.jsp" class="me-3">Menú Administrador</a>
            <a href="logout" class="">Cerrar sesión</a>
<%
        } else {
            // Usuario normal
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

    <!-- Mensaje superior de operaciones en catálogo -->
    <div id="mensajeCatalogo" style="max-width:800px;margin:10px auto 0 auto;text-align:center;"></div>

    <!-- CATALOGO DE PRODUCTOS -->
    <div class="catalogo">
        <% if (listaProductos != null && !listaProductos.isEmpty()) {
            for (ListaProductoDto p : listaProductos) { %>
            <div class="producto">
                <% if (p.getImagenBase64() != null && !p.getImagenBase64().trim().isEmpty()) { %>
                    <img src="data:image/jpeg;base64,<%= p.getImagenBase64() %>" alt="<%= p.getNombreProducto() %>"/>
                <% } else { %>
                    <img src="imagenes/Imagen_favicon.png" alt=""/>
                <% } %>
                <h3><%= p.getNombreProducto() %></h3>
                <p><%= p.getDescripcionProducto() %></p>
                <div class="precio"><%= p.getPrecioProducto() %> €</div>
                <button class="btn-agregar-carrito" data-id-producto="<%= p.getIdProducto() %>">Agregar al carrito</button>
            </div>
        <% } } else { %>
            <p>No hay productos disponibles.</p>
        <% } %>
    </div>
</div>

<!-- SCRIPTS -->
<script src="javascript/precargavista.js"></script>
<script src="javascript/busqueda.js"></script>
<script src="javascript/catalogoAcciones.js"></script>
</body>
</html>
