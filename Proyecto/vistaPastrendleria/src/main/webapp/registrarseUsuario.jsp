<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Registro | Dulce Sabor</title>
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

  <!-- CONTENIDO PRINCIPAL -->
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
          <h2>Crea tu cuenta</h2>

          <button class="google-btn">
            <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/google/google-original.svg" alt="Google">
            Registrarse con Google
          </button>

          <div class="divider"><span>o regístrate con tu correo</span></div>

          <!-- Formulario de registro -->
          <form id="registerUserForm" action="registroUsuario" method="POST"> 
            <!-- Nombre de usuario -->
            <div class="mb-3">
                <input type="text" id="nombreUsuario" name="nombreUsuario" class="input-field" placeholder="Introduzca su nombre" required>
            </div>
            
            <!-- Teléfono de usuario -->
            <div class="mb-3">
                <input type="text" id="telefonoUsuario" name="telefonoUsuario" class="input-field" placeholder="Introduzca su teléfono" required>
            </div>
        
            <!-- Correo -->
            <div class="mb-3">
                <input type="email" id="emailUsuario" name="emailUsuario" class="input-field" placeholder="Introduzca su email" required>
            </div>

            <!-- Contraseña -->
            <div class="mb-3">
                <input type="password" id="passwordUsuario" name="passwordUsuario" class="input-field" placeholder="Introduce la contraseña" required>
            </div>

            <!-- Confirmar Contraseña -->
            <div class="mb-3">
                <input type="password" id="confirmPasswordUsuario" name="confirmPasswordUsuario" class="input-field" placeholder="Repetir contraseña" required>
            </div>

            <!-- Botón de registro -->
            <button type="submit" class="login-btn">Registrarse</button>
          </form>

          <!-- Mensajes de error -->
          <div id="result" class="text-center mt-3 text-danger">
            <!-- Usando JSTL para mostrar los errores -->
            <c:if test="${not empty errorMessage}">
                <p class="error">${errorMessage}</p>
            </c:if>
          </div>

          <!-- Enlaces adicionales -->
          <div class="bottom-text">
            ¿Ya tienes una cuenta? <a href="iniciarSesionUsuario.jsp">Inicia sesión</a>
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
