const Cart = {
  key: 'ecom_cart',

  getItems() {
    return JSON.parse(localStorage.getItem(this.key) || '[]');
  },

  save(items) {
    localStorage.setItem(this.key, JSON.stringify(items));
    this.updateBadge();
  },

  add(product, quantity = 1) {
    const items = this.getItems();
    const existing = items.find(i => i.productId === product.productId);
    if (existing) {
      existing.quantity += quantity;
    } else {
      items.push({
        productId: product.productId,
        productName: product.productName,
        price: parseFloat(product.price),
        category: product.category,
        stockQuantity: product.stockQuantity,
        quantity: quantity
      });
    }
    this.save(items);
    UI.toast(`${product.productName} added to cart`);
  },

  remove(productId) {
    const items = this.getItems().filter(i => i.productId !== productId);
    this.save(items);
    if (document.getElementById('cart-items')) this.render();
  },

  updateQuantity(productId, quantity) {
    const items = this.getItems();
    const item = items.find(i => i.productId === productId);
    if (item) {
      item.quantity = Math.max(1, Math.min(parseInt(quantity) || 1, item.stockQuantity || 99));
      this.save(items);
      if (document.getElementById('cart-items')) this.render();
    }
  },

  clear() {
    localStorage.removeItem(this.key);
    this.updateBadge();
  },

  getCount() {
    return this.getItems().reduce((sum, i) => sum + i.quantity, 0);
  },

  getTotal() {
    return this.getItems().reduce((sum, i) => sum + (i.price * i.quantity), 0);
  },

  updateBadge() {
    const badge = document.getElementById('cart-count');
    if (badge) badge.textContent = this.getCount();
  },

  render(containerId = 'cart-items') {
    const container = document.getElementById(containerId);
    if (!container) return;
    const items = this.getItems();
    if (items.length === 0) {
      container.innerHTML = `
        <div class="empty-state">
          <i class="fas fa-shopping-basket"></i>
          <h3>Your cart is empty</h3>
          <p>Looks like you haven't added anything yet.</p>
          <a href="/products.html" class="btn btn-primary mt-2">Start Shopping</a>
        </div>`;
      const totalEl = document.getElementById('cart-total');
      if (totalEl) totalEl.textContent = '$0.00';
      return;
    }
    container.innerHTML = items.map(item => `
      <div class="cart-item">
        <div class="cart-item-info">
          <h4>${item.productName}</h4>
          <p class="text-muted">${item.category}</p>
          <p class="text-muted">$${item.price.toFixed(2)} each</p>
        </div>
        <div class="cart-item-qty">
          <input type="number" min="1" max="${item.stockQuantity || 99}" value="${item.quantity}" 
            onchange="Cart.updateQuantity(${item.productId}, this.value)">
        </div>
        <div class="cart-item-total">$${(item.price * item.quantity).toFixed(2)}</div>
        <button class="btn btn-danger btn-sm" onclick="Cart.remove(${item.productId})"><i class="fas fa-trash"></i></button>
      </div>
    `).join('');

    const totalEl = document.getElementById('cart-total');
    if (totalEl) totalEl.textContent = '$' + this.getTotal().toFixed(2);
    const subtotalEl = document.getElementById('cart-subtotal');
    if (subtotalEl) subtotalEl.textContent = '$' + this.getTotal().toFixed(2);
  }
};