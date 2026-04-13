// busquedaUsuarios.js
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInput');
    const tabla = document.querySelector('table tbody');

    if (!searchInput || !tabla) return;

    const filas = Array.from(tabla.querySelectorAll('tr'));

    const filtrar = () => {
        const term = searchInput.value.toLowerCase().trim();

        filas.forEach(fila => {
            const celdas = Array.from(fila.querySelectorAll('td'));
            const coincide = celdas.some(td => (td.textContent || '').toLowerCase().includes(term));
            fila.style.display = coincide ? '' : 'none';
        });
    };

    searchInput.addEventListener('input', filtrar);
});
