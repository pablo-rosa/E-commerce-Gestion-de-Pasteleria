<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Sitio en Mantenimiento</title>
    <link rel="icon" href="imagenes/Imagen_favicon.png" type="image/png">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="estilos/estilos-ventanas-mensaje.css">
    <link rel="stylesheet" href="estilos/estilos-precargavista.css">
</head>

<body>
  <!-- PRELOADER -->
  <div id="preloader">
    <img src="imagenes/Imagen_favicon.png" alt="Cargando Dulce Sabor">
  </div>

  <div id="content" style="display:none;">
    <div class="container" style="max-width: 500px; margin-top: 50px;">
        <!-- Imagen del logo -->
        <div class="text-center mt-3">
            <img width="148" height="118" src="imagenes/Imagen_favicon.png" alt="Logo Fuun" class="img-fluid">
        </div>

        <h3 class="text-center my-4"><b>Sitio en Mantenimiento</b></h3>

        <!-- Mensaje de mantenimiento -->
        <div class="alert alert-warning text-center" role="alert">
            Actualmente estamos realizando tareas de mantenimiento.<br />
            Por favor, intenta acceder nuevamente más tarde.<br />
            Gracias por tu paciencia.
        </div>

        <!-- Botón para volver al menú principal -->
        <div class="text-center">
            <a href="index.jsp" class="btn btn-dark w-100">Volver al menú principal</a>
        </div>

        <div class="text-center mt-3">
            <p>¿Necesitas ayuda? <a href="index.jsp">Contáctanos</a></p>
        </div>
    </div>
  </div>

  <!-- SCRIPT DE LA PRECARGA -->
  <script src="javascript/precargavista.js"></script>
</body>

</html>
