// Stripe.js
// Este script maneja la confirmación de compra y la redirección a Stripe Checkout

async function confirmarCompra() {
    const direccionInput = document.getElementById('direccion');
    const idUsuarioInput = document.getElementById('idUsuario');
    const msgEl = document.getElementById('mensajeCarrito');

    const direccion = direccionInput ? direccionInput.value.trim() : '';
    const idUsuario = idUsuarioInput ? idUsuarioInput.value.trim() : '';

    // Validaciones básicas
    if (!direccion) {
        if (msgEl) {
            msgEl.textContent = 'Debes ingresar una dirección.';
            msgEl.className = 'alert-danger';
        }
        return;
    }
    if (!idUsuario || idUsuario === '0') {
        if (msgEl) {
            msgEl.textContent = 'Debes iniciar sesión para realizar la compra.';
            msgEl.className = 'alert-danger';
        }
        return;
    }

    // Crear payload para CarritoControlador
    const payload = {
        accion: 'confirmar',
        direccionPedido: direccion,
        idUsuario: parseInt(idUsuario, 10)
    };

    try {
        // Confirmar la compra en el backend
        const response = await fetch('CarritoControlador', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        const result = await response.json();

        // Mostrar mensaje de error si es necesario
        if (result.mensaje.toLowerCase().includes('error') || result.mensaje.toLowerCase().includes('deb')) {
            if (msgEl) {
                msgEl.textContent = result.mensaje;
                msgEl.className = 'alert-danger';
            }
            return;
        }

        // ✅ Simulación de pago Stripe
        // Crear sesión de Stripe en backend
        const stripeResp = await fetch('stripe', { method: 'POST' });
        const stripeData = await stripeResp.json();

        // Redirigir a Stripe Checkout
        const stripe = Stripe("pk_test_51SqJajFTwqfdh4EMkganfehV7uHxuG07YRqC7B6G5xQcYJXwSML8Mb6zaAiiZrjr7GuJLzDDnM551L2VHCxo9aSt00yOiMKz5k");
        const { error } = await stripe.redirectToCheckout({ sessionId: stripeData.sessionId });

        // Mostrar error si algo falla en Stripe
        if (error && msgEl) {
            msgEl.textContent = error.message;
            msgEl.className = 'alert-danger';
        }

    } catch (e) {
        console.error(e);
        if (msgEl) {
            msgEl.textContent = 'Error al procesar la compra.';
            msgEl.className = 'alert-danger';
        }
    }
}
