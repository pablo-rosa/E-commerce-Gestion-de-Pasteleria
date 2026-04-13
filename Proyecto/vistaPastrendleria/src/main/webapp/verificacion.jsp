<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verificación de Cuenta</title>
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
            <!-- Imágen del logo -->
            <div class="text-center mt-3">
                <img width="148" height="118" src="imagenes/Imagen_favicon.png" alt="Logo Fuun" class="img-fluid">
            </div>

            <h3 class="text-center my-4"><b>Verificación de Cuenta</b></h3>

            <!-- Mensaje de información -->
            <div class="alert alert-info text-center" role="alert">
                Se ha enviado un enlace de verificación a la dirección de correo 
                <strong><%= request.getParameter("email") %></strong>. <br>
                Por favor, revise su bandeja de entrada y confirme su cuenta para completar el registro.
            </div>

            <!-- Mensaje adicional -->
            <div class="text-center mt-3">
                <p>Esta página se actualizará automáticamente una vez que verifique su cuenta.</p>
            </div>

            <!-- Lógica futura: refrescar y redirigir tras verificación -->
            <script>
                // Simulación de verificación futura (ejemplo de polling)
                setInterval(function () {
                    // Simulador de redirección tras verificación
                    setTimeout(function () {
                        window.location.href = 'iniciarSesionUsuario.jsp';
                    }, 10000); // 10 segundos
                }, 10000);
            </script>

            <!-- Enlace adicional -->
            <div class="text-center mt-3">
                <a href="index.jsp">Volver al menú principal</a>
            </div>
        </div>
    </div>

    <!-- SCRIPT DE LA PRECARGA -->
    <script src="javascript/precargavista.js"></script>
</body>

</html>
