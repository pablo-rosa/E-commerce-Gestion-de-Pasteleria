// Buscador sobre el catálogo ya renderizado en la vista (sin llamadas a productos.json)

document.addEventListener('DOMContentLoaded', () => {
  const searchInput = document.getElementById('searchInput');
  const liveSearchResults = document.getElementById('liveSearchResults');

  if (!searchInput) return;

  const catalogo = document.querySelector('.catalogo');
  if (!catalogo) return;

  const productos = Array.from(catalogo.querySelectorAll('.producto'));

  const filtrar = () => {
    const term = searchInput.value.toLowerCase().trim();

    if (liveSearchResults) {
      liveSearchResults.style.display = 'none';
      liveSearchResults.innerHTML = '';
    }

    if (!term) {
      productos.forEach(p => p.style.display = 'flex');
      return;
    }

    productos.forEach(p => {
      const titulo = (p.querySelector('h3')?.textContent || '').toLowerCase();
      const descripcion = (p.querySelector('p')?.textContent || '').toLowerCase();
      const coincide = titulo.includes(term) || descripcion.includes(term);
      p.style.display = coincide ? 'flex' : 'none';
    });
  };

  searchInput.addEventListener('input', filtrar);
});
