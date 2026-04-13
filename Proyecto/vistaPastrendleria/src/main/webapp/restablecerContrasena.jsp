<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restablecer Contraseña | Dulce Sabor</title>

    <link rel="icon" href="imagenes/Imagen_favicon.png" type="image/png">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="estilos/estilos-autentificacion.css">
    <link rel="stylesheet" href="estilos/estilos-precargavista.css">
</head>
<body>
  <!-- PRELOADER -->
  <div id="preloader">
    <img src="imagenes/Imagen_favicon.png" alt="Cargando Dulce Sabor">
  </div>

  <div id="content" style="display:none;">
    <div class="login-container">
      <div class="login-left">
        <img src="imagenes/Imagen_favicon.png" alt="Logo Dulce Sabor" class="logo">
        <h1>Dulce Sabor</h1>
        <h2>Repostería Artesanal & Gourmet</h2>
        <p>Descubre la dulzura en su máxima expresión.<br>Elaboramos postres únicos con ingredientes seleccionados.</p>
      </div>

      <div class="login-right">
        <div class="login-box">
          <img src="imagenes/Imagen_favicon.png" alt="Logo Dulce Sabor" class="small-logo">
          <h2>Establecer nueva contraseña</h2>

          <!-- Formulario para restablecer la contraseña -->
          <form action="procesarCambioContrasena" method="post">
            <!-- Token oculto pasado por URL -->
            <input type="hidden" name="token" value="<%= request.getParameter("token") %>">

            <!-- Nueva contraseña -->
            <div class="mb-3">
              <input type="password" name="nuevaPassword" class="input-field" placeholder="Nueva contraseña" required>
            </div>

            <!-- Confirmar nueva contraseña -->
            <div class="mb-3">
              <input type="password" name="confirmarPassword" class="input-field" placeholder="Confirmar contraseña" required>
            </div>

            <!-- Botón para actualizar la contraseña -->
            <button type="submit" class="login-btn w-100">Actualizar contraseña</button>
          </form>

          <!-- Mensaje de error utilizando JSTL -->
          <c:if test="${not empty errorMessage}">
            <div class="text-center mt-3 text-danger">
              <p class="error">${errorMessage}</p>
            </div>
          </c:if>

          <!-- Mensaje de éxito utilizando JSTL -->
          <c:if test="${not empty successMessage}">
            <div class="text-center mt-3 text-success">
              <p class="success">${successMessage}</p>
            </div>
          </c:if>

          <div class="bottom-text">
            <p><a href="index.jsp">↩ Volver al menú</a></p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- SCRIPT DE LA PRECARGA -->
  <script src="javascript/precargavista.js"></script>
</body>
</html>
