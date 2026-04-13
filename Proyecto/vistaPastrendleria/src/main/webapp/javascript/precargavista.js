window.addEventListener("load", () => {
  // Seleccionamos el preloader y el contenido
  const preloader = document.getElementById("preloader");
  const content = document.getElementById("content");

  if (content) {
    content.style.display = "block"; // Asegúrate de que el contenido esté visible

    // Añadimos la clase "visible" para la animación de opacidad
    setTimeout(() => content.classList.add("visible"), 100);

    // Desaparecemos el preloader
    setTimeout(() => {
      preloader.style.opacity = "0"; // Desvanecemos el preloader
      preloader.style.visibility = "hidden"; // Lo ocultamos
    }, 300); // Espera 300ms para permitir que la animación ocurra

    // Eliminamos el preloader del DOM después de 600ms (esperando que la animación haya terminado)
    setTimeout(() => preloader.remove(), 600);

    // Aseguramos que el cuerpo del documento pueda desplazarse normalmente después de que el preloader desaparezca
    document.body.style.overflow = "auto";
  }
});
