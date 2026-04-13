<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    // Recuperar rol desde sesión
    String rol = (String) session.getAttribute("rol");
    if (rol == null || !"admin".equals(rol)) {
        request.setAttribute("errorMensaje", "No tienes permisos para acceder a esta página.");
        request.getRequestDispatcher("iniciarSesionUsuario.jsp").forward(request, response);
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel de Administración</title>
    <style>
        body { font-family: Arial, sans-serif; text-align:center; padding-top:50px; background:#f5f5f5; }
        h1 { margin-bottom:40px; }
        .btn { display:inline-block; padding:15px 25px; margin:10px; font-size:16px; background:#007bff; color:#fff; border:none; border-radius:8px; cursor:pointer; text-decoration:none; }
        .btn:hover { background:#0056b3; }
    </style>
</head>
<body>

<h1>Panel de Administración</h1>

<!-- Botones para ir a las diferentes páginas -->
<a href="listaUsuarios" class="btn">Lista de Usuarios</a>
<a href="pedidos" class="btn">Pedidos</a>
<a href="catalogo.jsp" class="btn">Catálogo de Productos</a>

</body>
</html>
