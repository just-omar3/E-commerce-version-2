const UI = {
  toast(msg, type = 'success') {
    const existing = document.querySelector('.toast');
    if (existing) existing.remove();
    const div = document.createElement('div');
    div.className = `toast toast-${type}`;
    div.innerHTML = `<i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'exclamation-circle' : 'info-circle'}"></i> ${msg}`;
    document.body.appendChild(div);
    requestAnimationFrame(() => div.classList.add('show'));
    setTimeout(() => {
      div.classList.remove('show');
      setTimeout(() => div.remove(), 300);
    }, 3500);
  },

  formatPrice(price) {
    return '$' + parseFloat(price).toFixed(2);
  },

  formatDate(dateStr) {
    if (!dateStr) return '-';
    return new Date(dateStr).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
  },

  showLoading(elId) {
    const el = document.getElementById(elId);
    if (el) el.innerHTML = '<div class="spinner"></div>';
  },

  renderProductCard(p) {
    const stockClass = p.stockQuantity > 10 ? 'stock-in' : p.stockQuantity > 0 ? 'stock-low' : 'stock-out';
    const stockText = p.stockQuantity > 10 ? 'In Stock' : p.stockQuantity > 0 ? `Only ${p.stockQuantity} left` : 'Out of Stock';
    return `
      <div class="card product-card" data-id="${p.productId}">
        <div class="product-image">
          <i class="fas fa-box"></i>
          ${p.stockQuantity < 5 ? '<span class="product-badge">Hot</span>' : ''}
        </div>
        <div class="card-body">
          <div class="card-title">${p.productName}</div>
          <div class="card-text">${p.category}</div>
          <div class="card-price"><span class="currency">$</span>${parseFloat(p.price).toFixed(2)}</div>
          <div class="product-meta">
            <span class="stock-badge ${stockClass}">${stockText}</span>
            <button class="btn btn-primary btn-sm" onclick="ProductPage.addToCart(${p.productId})" ${p.stockQuantity <= 0 ? 'disabled' : ''}>
              <i class="fas fa-cart-plus"></i> Add
            </button>
          </div>
        </div>
      </div>
    `;
  },

  openModal(id) {
    const modal = document.getElementById(id);
    if (modal) modal.classList.add('active');
  },

  closeModal(id) {
    const modal = document.getElementById(id);
    if (modal) modal.classList.remove('active');
  },

  initModals() {
    document.querySelectorAll('.modal-overlay').forEach(m => {
      m.addEventListener('click', e => { if (e.target === m) m.classList.remove('active'); });
    });
  }
};

const ProductPage = {
  async addToCart(productId) {
    try {
      const product = await API.getProduct(productId);
      Cart.add(product);
    } catch (e) {
      UI.toast(e.message, 'error');
    }
  }
};