document.addEventListener('DOMContentLoaded', () => {
  Auth.initUser().then(() => {
    Auth.updateNav();
  });
  UI.initModals();
  Cart.updateBadge();


  const searchInput = document.getElementById('global-search');
  if (searchInput) {
    searchInput.addEventListener('keypress', e => {
      if (e.key === 'Enter') {
        const q = encodeURIComponent(searchInput.value);
        window.location.href = `/products.html?search=${q}`;
      }
    });
  }
});