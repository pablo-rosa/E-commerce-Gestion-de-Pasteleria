// Acciones de catálogo: añadir productos al carrito y mostrar mensajes en la vista

document.addEventListener('DOMContentLoaded', () => {
  const botones = document.querySelectorAll('.btn-agregar-carrito');
  const msgEl = document.getElementById('mensajeCatalogo');

  if (!botones || botones.length === 0) return;

  botones.forEach((btn) => {
    btn.addEventListener('click', async () => {
      const idProducto = btn.getAttribute('data-id-producto');
      if (!idProducto) return;

      try {
        const response = await fetch('CarritoControlador', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ accion: 'agregar', idProducto: parseInt(idProducto, 10) })
        });

        const result = await response.json();

        if (msgEl) {
          msgEl.innerHTML = `<div class="alert-success">${result.mensaje || 'Producto agregado al carrito.'}</div>`;
        }
      } catch (err) {
        console.error(err);
        if (msgEl) {
          msgEl.innerHTML = '<div class="alert-danger">Error al agregar el producto.</div>';
        }
      }
    });
  });
});

