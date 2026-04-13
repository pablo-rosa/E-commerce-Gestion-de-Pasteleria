<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Contraseña Restablecida</title>
    <link rel="icon" href="imagenes/Imagen_favicon.png" type="image/png">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="estilos/estilos-ventanas-mensaje.css">
    <link rel="stylesheet" href="estilos/estilos-precargavista.css">
    <script type="text/javascript">
        let tiempoRestante = 5;
        function cuentaAtras() {
            if (tiempoRestante > 0) {
                document.getElementById("contador").innerText = tiempoRestante;
                tiempoRestante--;
            } else {
                // Redirigir a IniciarSesionUsuario (ajusta si es JSP o servlet)
                window.location.href = "iniciarSesionUsuario.jsp";
            }
        }
        setInterval(cuentaAtras, 1000);
    </script>
</head>

<body>
  <!-- PRELOADER -->
  <div id="preloader">
    <img src="imagenes/Imagen_favicon.png" alt="Cargando Dulce Sabor">
  </div>

  <div id="content" style="display:none;">
    <div class="container" style="width: 400px; margin-top: 50px;">
        <!-- Imagen del logo -->
        <div class="text-center mt-3">
            <img width="148" height="118" src="imagenes/Imagen_favicon.png" alt="Logo Fuun" class="img-fluid">
        </div>

        <h3 class="text-center mb-4"><b>Contraseña Restablecida</b></h3>

        <div class="alert alert-success text-center" role="alert" style="font-size: 1.1rem;">
            ¡Contraseña restablecida exitosamente!<br />
            Serás redirigido a iniciar sesión en <span id="contador">5</span> segundos.
        </div>

    </div>
  </div>

  <!-- SCRIPT DE LA PRECARGA -->
  <script src="javascript/precargavista.js"></script>
</body>

</html>
