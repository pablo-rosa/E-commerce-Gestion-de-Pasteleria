// Lógica de interacción del carrito en la vista (MVC: la vista sólo llama a este JS)

async function modificarCarrito(accion, idProducto, cantidad = 0) {
  try {
    const response = await fetch('CarritoControlador', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ accion, idProducto, cantidad })
    });

    const result = await response.json();

    // El mensaje se mostrará tras recargar la página usando un atributo de sesión
    // (el controlador guarda el mensaje en sesión). Aquí sólo recargamos para
    // que los datos del carrito estén siempre coherentes con el backend.
    window.location.reload();
  } catch (e) {
    console.error(e);
    const msgEl = document.getElementById('mensajeCarrito');
    if (msgEl) {
      msgEl.textContent = 'Error al actualizar el carrito.';
      msgEl.className = 'alert-danger';
    }
  }
}

async function confirmarCompra() {
  const direccionInput = document.getElementById('direccion');
  const idUsuarioInput = document.getElementById('idUsuario');
  const msgEl = document.getElementById('mensajeCarrito');

  const direccion = direccionInput ? direccionInput.value.trim() : '';
  const idUsuario = idUsuarioInput ? idUsuarioInput.value.trim() : '';

  if (!msgEl) {
    console.error('Elemento mensajeCarrito no encontrado');
  }

  if (!direccion) {
    if (msgEl) {
      msgEl.textContent = 'Debes ingresar una dirección.';
      msgEl.className = 'alert-danger';
    }
    return;
  }

  if (!idUsuario) {
    if (msgEl) {
      msgEl.textContent = 'Error: ID de usuario no disponible.';
      msgEl.className = 'alert-danger';
    }
    return;
  }

  const payload = {
    accion: 'confirmar',
    direccionPedido: direccion,
    idUsuario: parseInt(idUsuario, 10)
  };

  try {
    const response = await fetch('CarritoControlador', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    const result = await response.json();

    // El mensaje de resultado se mostrará tras recargar usando atributo de sesión
    window.location.reload();
  } catch (e) {
    console.error('Error al procesar la compra:', e);
    if (msgEl) {
      msgEl.textContent = 'Error al procesar la compra.';
      msgEl.className = 'alert-danger';
    }
  }
}

