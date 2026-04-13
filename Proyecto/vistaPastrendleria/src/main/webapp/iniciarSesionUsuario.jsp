<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión | Dulce Sabor</title>
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
                    <h2>Inicia sesión</h2>

                    <button class="google-btn">
                        <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/google/google-original.svg" alt="Google">
                        Continuar con Google
                    </button>

                    <div class="divider"><span>o usa tu cuenta</span></div>

                    <!-- Formulario de inicio de sesión -->
                    <form id="loginUserForm" action="loginUsuario" method="post">
                        <input type="email" id="email" name="email" class="input-field" placeholder="Correo electrónico" required>
                        <input type="password" id="password" name="password" class="input-field" placeholder="Contraseña" required>
                        <button type="submit" class="login-btn">Entrar</button>
                    </form>

                    <!-- Mensajes de error o éxito -->
                    <div class="text-center mt-3">
                        <!-- Mostrar mensaje de error si existe -->
                        <c:if test="${not empty errorMessage}">
                            <p class="error text-danger">${errorMessage}</p>
                        </c:if>

                        <!-- Mostrar mensaje de éxito si existe -->
                        <c:if test="${not empty successMessage}">
                            <p class="success text-success">${successMessage}</p>
                        </c:if>
                    </div>

                    <div class="bottom-text">
                        ¿No tienes una cuenta? <a href="registrarseUsuario.jsp">Regístrate</a><br>
                        <a href="recuperarContrasena.jsp">¿Olvidaste tu contraseña?</a>
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
