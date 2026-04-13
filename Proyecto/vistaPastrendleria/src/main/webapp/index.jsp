<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true" %>
<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Pastrendlería | Chocolate Dubái</title>
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
  <link href="https://fonts.googleapis.com/css2?family=Baloo+2:wght@500;600&family=Suez+One&display=swap">

</head>

<body>
  <div id="content">
    <!-- PRELOADER -->
    <div id="preloader">
      <img src="imagenes/Imagen_favicon.png" alt="Cargando Dulce Sabor">
    </div>
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


    <!-- BANNER -->
    <section class="banner">
      <div class="banner-text">
        <h3>¡NUEVA COLECCIÓN!</h3>
        <h1>CHOCOLATE <span>DUBAI</span></h1>
        <p>Nueva colección de dulces con chocolate de Dubái<br>desde el 1-7 enero 2025</p>
        <a href="catalogo"><button class="btn">CATÁLOGO</button></a>
      </div>
      <div class="banner-img">
        <img src="imagenes/Imagen_chocolate-dubai.png" alt="Chocolate Dubái">
      </div>
    </section>

    <!-- PRODUCTOS -->
    <section class="productos">
      <h2>EL DULCE QUE SIEMPRE TE ACOMPAÑA</h2>
      <div class="productos-grid">
        <div class="producto">
          <img src="imagenes/Imagen_crumble-cookies.png" alt="Galletas">
          <h3>Galletas Artesanales</h3>
          <p>Chocolate chip, avena, pasas y mantequilla de maní</p>
        </div>
        <div class="producto">
          <img src="imagenes/Imagen_tarta-lotus.png" alt="Pastel">
          <h3>Pasteles con Sabores Populares</h3>
          <p>Chocolate, red velvet, zanahoria y limón</p>
        </div>
        <div class="producto">
          <img src="imagenes/Imagen_bombones.png" alt="Chocolates">
          <h3>Dulces y Chocolates</h3>
          <p>Arándano, manzana, durazno y crema de plátano</p>
        </div>
        <div class="producto">
          <img src="imagenes/Imagen_dulces-variados.png" alt="Dulces">
          <h3>Dulces Artesanales</h3>
          <p>Bombones, caramelos y trufas de sabores internacionales</p>
        </div>
      </div>
      <!-- Botón unificado -->
      <a href="catalogo">
        <button class="btn">PRODUCTOS</button>
      </a>

    </section>
    <!-- PRODUCTOS MÁS DESTACADOS -->
    <section class="productos-destacados">
      <h2>Productos Más Destacados</h2>
      <div id="productosDestacadosContainer" class="productos-grid">
        <!-- Aquí se mostrarán los productos más destacados -->
      </div>
    </section>



    <!-- INFO -->
    <section class="info">
      <h2>La pastelería más actualizada de Sevilla</h2>
      <p>En <strong>Pastrendlería</strong>, creemos que cada día es una nueva oportunidad para <em>aventurarse a
          probar</em> y disfrutar de cosas nuevas. Aventúrate a probar el dulzor que recorren las mayores
        <strong>tendencias del mundo</strong>, y disfruta de la experiencia única que ofrecemos.
      </p>

      <div class="info-icons">
        <div class="icon-item" aria-label="Escoge tus dulces favoritos y añádelos al carrito">
          🛒
          <p>Escoge tus dulces favoritos y añádelos al carrito</p>
        </div>
        <div class="icon-item" aria-label="Recíbelos directamente en tu puerta">
          🏠
          <p>Recíbelos directamente en tu puerta</p>
        </div>
        <div class="icon-item" aria-label="Garantizamos calidad y una experiencia inolvidable">
          ⭐
          <p>Garantizamos calidad y una experiencia inolvidable</p>
        </div>
        <div class="icon-item" aria-label="Nos encanta alegrar tu corazón con un poco de azúcar">
          😊
          <p>Nos encanta alegrar tu corazón con un poco de azúcar</p>
        </div>
      </div>

      <!-- Testimonios horizontales -->
      <div class="testimonials">
        <div class="testimonial">
          <blockquote>
            <p>"Los pasteles de Pastrendlería son simplemente deliciosos. Cada bocado es una explosión de sabor. ¡Los
              recomiendo a todos mis amigos!"</p>
            <footer>- María G., cliente feliz</footer>
          </blockquote>
        </div>
        <div class="testimonial">
          <blockquote>
            <p>"Excelente servicio y productos de calidad. Mis hijos no pueden dejar de pedir sus dulces favoritos. ¡Una
              experiencia maravillosa!"</p>
            <footer>- Juan P., cliente recurrente</footer>
          </blockquote>
        </div>
        <div class="testimonial">
          <blockquote>
            <p>"El envío fue rápido y los productos llegaron perfectos. Se nota que cuidan cada detalle,
              ¡definitivamente volveré a comprar!"</p>
            <footer>- Laura S., cliente satisfecha</footer>
          </blockquote>
        </div>
        <div class="testimonial">
          <blockquote>
            <p>"Una pastelería que combina creatividad y sabor. Cada pastel es único y delicioso. ¡Superaron mis
              expectativas!"</p>
            <footer>- Carlos M., amante de los dulces</footer>
          </blockquote>
        </div>
      </div>

      <!-- CTA de contacto -->
      <div class="cta">
        <p>¿Listo para disfrutar del mejor dulzor de Sevilla?</p>
        <a href="#contacto" class="btn">Contáctanos ahora</a>
      </div>
    </section>



    <!-- UBICACIÓN / MAPA -->
    <section class="ubicacion">
      <div class="ubicacion-texto">
        <h2>Nuestra Ubicación</h2>
        <p>Pastrendlería Trendy</p>
        <p>Calle de la Dulzura 123, Sevilla, España</p>
      </div>
      <div class="ubicacion-mapa">
        <a href="https://maps.app.goo.gl/t9Efis2Yxdd7P6D8A" target="_blank">
          <iframe
            src="https://maps.google.com/maps?q=37.376759,-5.946991&hl=es;z=15&output=embed&markers=37.376759,-5.946991"
            width="100%" height="250" style="border:0;" allowfullscreen loading="lazy">
          </iframe>
        </a>
      </div>
    </section>

    <!-- FOOTER -->
    <footer class="footer">
      <div class="footer-top">
        <div class="footer-links-section">
          <h4>Información y datos legales</h4>
          <ul>
            <li><a href="#">Aviso legal</a></li>
            <li><a href="#">Sobre nosotros</a></li>
            <li><a href="#">Contacte con nosotros</a></li>
            <li><a href="#">Envío</a></li>
            <li><a href="#">Términos y condiciones</a></li>
            <li><a href="#">Política de Privacidad</a></li>
            <li><a href="#">Declaración Accesibilidad</a></li>
            <li><a href="#">Política de Cookies</a></li>
            <li><a href="#">Mapa del sitio</a></li>
          </ul>
        </div>
        <div class="footer-contact">
          <h4>Contáctanos</h4>
          <p>Pastelería Online</p>
          <p>Écija (Sevilla)</p>
          <p>Tel: 665 098 163</p>
          <p>Email: <a href="mailto:ventas@pasteleriaonline.es">ventas@pasteleriaonline.es</a></p>
          <p>Puedes ponerte en contacto con nosotros, te responderemos lo antes posible.</p>
        </div>
        <div class="footer-newsletter">
          <h4>Boletín de noticias</h4>
          <form>
            <input type="email" placeholder="Su dirección de correo electrónico">
            <label>
              <input type="checkbox" required>
              He leído y acepto la política de privacidad.
            </label>
            <button type="submit">Suscribirse</button>
          </form>
          <p>Puede darse de baja en cualquier momento. Consulte nuestra información de contacto en el aviso legal.</p>
        </div>
      </div>
      <div class="footer-middle">
        <img src="emblemas-kit-digital.jpg" alt="Emblemas Kit Digital">
        <p>Financiado por la Unión Europea - NextGenerationEU</p>
      </div>
      <div class="footer-bottom">
        <p>Síguenos:
          <a href="#">Instagram</a> |
          <a href="#">Facebook</a> |
          <a href="#">Twitter</a>
        </p>
        <p>© 2025 Pastrendlería. Todos los derechos reservados.</p>
      </div>
    </footer>
  </div>
  <!-- SCRIPT DE LA PRECARGA -->
  <script src="javascript/precargavista.js"></script>
  <script src="javascript/busqueda.js"></script>
  <!-- Añadir el archivo JS que maneja la lista de productos destacados -->
  <script src="javascript/listaproductos.js"></script>ç
  <script src="javascript/precargavista.js"></script>


</body>

</html>