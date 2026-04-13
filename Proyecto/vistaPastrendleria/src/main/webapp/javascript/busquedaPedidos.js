// busquedaPedidos.js
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInputPedidos');
    const tabla = document.querySelector('table tbody');

    if (!searchInput || !tabla) return;

    const filas = Array.from(tabla.querySelectorAll('tr'));

    const filtrar = () => {
        const term = searchInput.value.toLowerCase().trim();

        filas.forEach(fila => {
            const celdas = Array.from(fila.querySelectorAll('td'));
            let coincide = false;

            celdas.forEach(td => {
                // Para la columna de detalles (ul > li) buscamos en todo el texto
                const texto = td.textContent || '';
                if (texto.toLowerCase().includes(term)) {
                    coincide = true;
                }
            });

            fila.style.display = coincide ? '' : 'none';
        });
    };

    searchInput.addEventListener('input', filtrar);
});
