<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recuperar Contraseña | Dulce Sabor</title>

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
          <h2>Recuperar Contraseña</h2>

          <!-- Formulario de recuperación de contraseña -->
          <form id="recuperarForm" action="enviarRecuperacion" method="post">
            <!-- Correo de usuario -->
            <input type="email" id="emailUsuario" name="emailUsuario" class="input-field" placeholder="Introduzca su correo" required>

            <!-- Botón de enviar enlace -->
            <button type="submit" class="login-btn">Enviar enlace</button>
          </form>

          <!-- Mensaje de error -->
          <div class="text-center mt-3 text-danger">
            <c:if test="${not empty errorMessage}">
                <p class="error">${errorMessage}</p>
            </c:if>
          </div>

          <!-- Mensaje de éxito -->
          <div class="text-center mt-3 text-success">
            <c:if test="${not empty successMessage}">
                <p class="success">${successMessage}</p>
            </c:if>
          </div>

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
